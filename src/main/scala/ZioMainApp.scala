package io.andrelucas

import event.application.zio.{EventRoute, EventZIOServiceImp}
import event.infra.EventConfiguration
import partner.application.zio.{PartnerRoute, PartnerZIOService, PartnerZIOServiceImp}
import partner.infra.PartnerConfiguration

import slick.jdbc.JdbcBackend.Database
import zio.*
import zio.http.*

object ZioMainApp extends ZIOAppDefault {
  private val db = Database.forConfig("ddd")

  private val partnerRoute = PartnerRoute()
  private val eventRoute = EventRoute()

  def run =
    Server
      .serve(
        partnerRoute.toHttpApp ++ eventRoute.toHttpApp
      )
      .provide(
        Server.defaultWithPort(8080),

        // An layer responsible for storing the state of the `counterApp`
        ZLayer.fromZIO(Ref.make(0)),

        // To use the persistence layer, provide the `PersistentUserRepo.layer` layer instead
        PartnerZIOServiceImp(PartnerConfiguration(db).partnerService).layer,
        EventZIOServiceImp(EventConfiguration(db).eventService).layer
      )
}
