package io.andrelucas
package event.application

import common.domain.DomainException
import common.domain.valueobjects.Name
import event.domain.entities.{Event, Partner}
import event.domain.repository.{EventRepository, PartnerRepository}
import event.infra.repositories.{EventPhysicalRepository, PartnerPhysicalRepository}

import java.time.{Duration, LocalDateTime}
import java.util.UUID
import scala.collection.mutable

class EventServiceIntegrationTest extends IntegrationSpec {
  
  private val eventRepository: EventRepository = EventPhysicalRepository(db)
  private val partnerRepository: PartnerRepository = PartnerPhysicalRepository(db)
  
  private val subject = EventService(eventRepository, partnerRepository)

  it should "return exception when is trying create a partner's a event without it is registered" in {
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

    val eventDate = LocalDateTime.of(2024, 11, 9, 20, 0, 0)
    val input = CreateEventInput("Knot Fest", "Knot Fest", eventDate, id)
    val either = subject.create(input)

    either.isRight should equal(true)

    val eventsCreated = eventRepository.findByPartnerId(id)
    eventsCreated should have length 1

    val expected = Event(eventsCreated.last.id, "Knot Fest", "Knot Fest", eventDate, 0, 0, false, id, mutable.Set.empty)
    eventsCreated should contain (expected)
  }
  
  it should "create return a exception when try creating a section without its event not exists" in {
    val eventId = UUID.randomUUID()
    val input = CreateEventSectionInput("VIP", "Knot Fest - VIP Section", 10000, 100, 0)
    val either = subject.createSection(eventId, input)

    either.left.e should be(Left(DomainException(s"the event $eventId wasn't created")))
  }

  it should "create a section when a event already was created" in {
    val partnerId = UUID.randomUUID()
    val partner = Partner(partnerId, Name("Andre Lucas"))
    partnerRepository.save(partner)

    val eventId = createNewEvent(partnerId)
    val input = CreateEventSectionInput("VIP", "Knot Fest - VIP Section", 10000, 100, 0)
    val either = subject.createSection(eventId, input)

    Thread.sleep(Duration.ofMillis(500))

    val eventCreated = eventRepository.findById(eventId).head
    eventCreated.sections.size should be (1)
    eventCreated.totalSpots should be (100)
    eventCreated.totalSpotsReserved should be (0)
    eventCreated.sections.last.spots.size should be (100)

    forAll(eventCreated.sections.last.spots.map(_.isReserved)) {
      s => s should be (false)
    }

    forAll(eventCreated.sections.last.spots.map(_.isPublished)) {
      s => s should be(false)
    }
  }

  private def createNewEvent(partnerId: UUID): UUID =
    val eventDate = LocalDateTime.of(2024, 11, 9, 20, 0, 0)
    subject.create(CreateEventInput("Knot Fest", "Knot Fest", eventDate, partnerId))
    eventRepository.findByPartnerId(partnerId).last.id
}
