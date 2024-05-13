package io.andrelucas
package event.infra

import event.application.EventService
import event.domain.repository.EventRepository
import event.infra.repositories.EventPhysicalRepository
import partner.domain.repository.PartnerRepository
import partner.infra.repository.PartnerPhysicalRepository

import slick.jdbc.JdbcBackend.Database

private class EventConfiguration(service: EventService):
  val eventService: EventService = service
  
object EventConfiguration  {
  def apply(db: Database): EventConfiguration = {
    val eventRepository: EventRepository = EventPhysicalRepository(db)
    val partnerRepository: PartnerRepository = PartnerPhysicalRepository(db)
    val eventService: EventService = EventService(eventRepository, partnerRepository)

    new EventConfiguration(eventService)
  }
}
