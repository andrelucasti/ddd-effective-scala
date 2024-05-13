package io.andrelucas
package partner.application.zio


import partner.application.PartnerService

import zio.*

trait PartnerZIOService:
  def save(partnerInput: PartnerInput): Task[Unit]

object PartnerZIOService:
  def save(partnerInput: PartnerInput): ZIO[PartnerZIOService, Throwable, Unit] =
    ZIO.serviceWithZIO[PartnerZIOService](_.save(partnerInput))


case class PartnerZIOServiceImp(private val partnerService: PartnerService) extends PartnerZIOService:
  override def save(partnerInput: PartnerInput): Task[Unit] = {
    ZIO.fromFuture{ ec =>
      partnerService.register(partnerInput)
    }
  }

  def layer: ZLayer[Any, Nothing, PartnerZIOServiceImp] =
    ZLayer {
      ZIO.succeed(PartnerZIOServiceImp(partnerService))
    }
