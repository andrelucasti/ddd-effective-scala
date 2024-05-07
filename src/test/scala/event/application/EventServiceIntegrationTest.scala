package io.andrelucas
package event.application

import common.domain.valueobjects.Name
import common.domain.{DomainEvent, DomainEventManager, DomainException, DomainPublisher}
import event.domain.entities.Event
import event.domain.repository.EventRepository
import event.infra.repositories.EventPhysicalRepository
import partner.domain.Partner
import partner.domain.repository.PartnerRepository
import partner.infra.repository.PartnerPhysicalRepository

import io.andrelucas.event.domain.domainevents.EventCreated
import io.andrelucas.event.infra.EventPublisher
import ox.channels.Channel

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.mutable
import scala.concurrent.Future

class EventServiceIntegrationTest extends IntegrationSpec {
  private val eventRepository: EventRepository = EventPhysicalRepository(db)
  private val partnerRepository: PartnerRepository = PartnerPhysicalRepository(db)

  private val channelEventCreated: Channel[EventCreated] = Channel.withCapacity(10)
  private val eventPublisher = EventPublisher(channelEventCreated)
  private val eventService = EventService(eventRepository, partnerRepository, eventPublisher)

  it should "return exception when is trying create a partner's a event without it is registered" in {
    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))
    val input = CreateEventInput("Knot Fest", "Knot Fest", LocalDateTime.of(2024, 11, 9, 20, 0, 0))
    val either = eventService.create(id, input)

    either map { e => e.toEither.left.e should be(Left(DomainException(s"the partner $id not exists yet"))) }
  }

  it should "create a event when partner is registered" in {
    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))

    val eventDate = LocalDateTime.of(2024, 11, 9, 20, 0, 0)
    val input = CreateEventInput("Knot Fest", "Knot Fest", eventDate)

    for
      _ <- partnerRepository.save(partner)
      e <- eventService.create(id, input)
      events <- eventRepository.findByPartnerId(id)
      event = events.last
    yield
      events should have length 1
      events should contain(Event(event.id, "Knot Fest", "Knot Fest", eventDate, 0, 0, false, id, mutable.Set.empty))

  }

  it should "create return a exception when try creating a section without its event not exists" in {
    val eventId = UUID.randomUUID()
    val input = CreateEventSectionInput("VIP", "Knot Fest - VIP Section", 10000, 100, 0)

    eventService.createSection(eventId, input) map { e =>
      e.toEither.left.e should be(Left(DomainException(s"the event $eventId wasn't created yet")))
    }
  }

  it should "create a event section when a event is registered" in {
    val partnerId = UUID.randomUUID()
    val partner = Partner(partnerId, Name("Andre Lucas"))

    val eventDate = LocalDateTime.of(2024, 11, 9, 20, 0, 0)
    val eventId = UUID.randomUUID()
    val event = Event(eventId, "Rock in Rio", "Bla", eventDate, 0, 0, false, partnerId, mutable.Set.empty)

    val eventSectionInput = CreateEventSectionInput("VIP", "Vip RR", 10000, 3, 0)

    for
      _ <- partnerRepository.save(partner)
      _ <- eventRepository.save(event)
      _ <- eventService.createSection(eventId, eventSectionInput)
      _ <- eventRepository.findById(eventId) // I don't know why I'm needing forcing here....
    yield
      val eventUpdated = eventRepository.findById(eventId).futureValue
      eventUpdated.get.id should be (eventId)
      eventUpdated.get.partnerId should be(partnerId)
      eventUpdated.get.totalSpots should be(3)
      eventUpdated.get.totalSpotsReserved should be(0)
      eventUpdated.get.sections.size should be(1)
  }
}
