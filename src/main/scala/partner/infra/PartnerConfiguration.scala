package io.andrelucas
package partner.infra

import common.domain.{DomainEvent, DomainPublisher}
import partner.application.{PartnerPublisher, PartnerActorPublisher, PartnerService}
import partner.domain.repository.PartnerRepository
import partner.infra.repository.PartnerPhysicalRepository

import io.andrelucas.partner.application.consumers.PartnerConsumer
import io.andrelucas.partner.domain.domainevents.PartnerEvent
import org.apache.pekko.actor.typed.{ActorSystem, Behavior}
import slick.jdbc.JdbcBackend.Database

private class PartnerConfiguration(service: PartnerService):
  val partnerService: PartnerService = service;

case object PartnerConfiguration {
  
  def apply(db: Database): PartnerConfiguration = {
    val partnerRepository: PartnerRepository = PartnerPhysicalRepository(db)
    val partnerPublisher: PartnerPublisher = PartnerActorPublisher(PartnerActorConfiguration())
    
    val partnerService: PartnerService = PartnerService(partnerRepository, partnerPublisher)
    
    new PartnerConfiguration(partnerService)
  }
}

object PartnerActorConfiguration:
  def apply(): ActorSystem[PartnerEvent] =
    ActorSystem(PartnerConsumer(), "PartnerHandler")
