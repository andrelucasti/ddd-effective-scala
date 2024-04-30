package io.andrelucas
package event.application

import common.domain.valueobjects.Name
import event.domain.entities.Partner
import event.domain.repository.{EventRepository, PartnerRepository}

import io.andrelucas.common.domain.DomainException
import io.andrelucas.event.infra.repositories.{EventPhysicalRepository, PartnerPhysicalRepository}

import java.time.LocalDateTime
import java.util.UUID

class EventServiceIntegrationTest extends IntegrationSpec {

  private val eventRepository: EventRepository = EventPhysicalRepository(db)
  private val partnerRepository: PartnerRepository = PartnerPhysicalRepository(db)
  
  private val subject = EventService(eventRepository, partnerRepository)

  it should "return exception when is trying create a partner's a event without it registered" in {
    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))
    val input = CreateEventInput("Knot Fest", "Knot Fest", LocalDateTime.of(2024, 11, 9, 20, 0, 0), id)
    val either = subject.create(input)

    either.left.e should be(Left(DomainException(s"the partner $id not exist")))
  }

  it should "create a event when partner is registered" in {
    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))
    partnerRepository.save(partner)
    
    val input = CreateEventInput("Knot Fest", "Knot Fest", LocalDateTime.of(2024, 11, 9, 20, 0, 0), id)
    val either = subject.create(input)

    either.isRight should equal(true)

    val eventsCreated = eventRepository.findByPartnerId(id)

    eventsCreated should have length(1)
  }
}
