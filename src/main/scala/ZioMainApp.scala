package io.andrelucas

import partner.application.zio.{PartnerRoute, PartnerZioRepo, PersistencePartnerRepo}

import zio.*
import zio.http.*

object ZioMainApp extends ZIOAppDefault {
  private val partnerRoute = PartnerRoute()

  def run =
    Server
      .serve(
        partnerRoute.toHttpApp
      )
      .provide(
        Server.defaultWithPort(8080),

        // An layer responsible for storing the state of the `counterApp`
        ZLayer.fromZIO(Ref.make(0)),

        // To use the persistence layer, provide the `PersistentUserRepo.layer` layer instead
        PersistencePartnerRepo.layer
      )
}
