package io.andrelucas
package partner.application.zio

import event.application.PartnerInput
import partner.application.PartnerService
import partner.domain.repository.PartnerRepository
import partner.infra.repository.PartnerPhysicalRepository

import io.andrelucas.partner.infra.PartnerConfiguration
import slick.jdbc.JdbcBackend.Database
import zio.*

trait PartnerZioRepo:
  def save(partnerInput: PartnerInput): Task[Unit]

object PartnerZioRepo:
  def save(partnerInput: PartnerInput): ZIO[PartnerZioRepo, Throwable, Unit] =
    ZIO.serviceWithZIO[PartnerZioRepo](_.save(partnerInput))

case class PersistencePartnerRepo(private val partnerService: PartnerService) extends PartnerZioRepo:
  override def save(partnerInput: PartnerInput): Task[Unit] = {
    ZIO.fromFuture{ ec =>
      partnerService.register(partnerInput)
    }
  }

object PersistencePartnerRepo:
  def layer: ZLayer[Any, Nothing, PersistencePartnerRepo] =
    ZLayer {
      ZIO.succeed(PersistencePartnerRepo(PartnerConfiguration.partnerService))
    }
