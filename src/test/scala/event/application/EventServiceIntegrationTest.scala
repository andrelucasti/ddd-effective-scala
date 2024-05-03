package io.andrelucas
package event.application

import common.domain.DomainException
import common.domain.valueobjects.Name
import event.domain.entities.{Event, Partner}
import event.domain.repository.{EventRepository, PartnerRepository}
import event.infra.repositories.{EventPhysicalRepository, PartnerPhysicalRepository}

import org.scalatest.concurrent.Eventually.eventually

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.mutable
import scala.concurrent.Await
import org.scalatest.concurrent.ScalaFutures.*

import scala.concurrent.duration.*
import org.scalatest.time.SpanSugar.*

import scala.concurrent.ExecutionContext.Implicits.global

class EventServiceIntegrationTest extends IntegrationSpec {

  private val eventRepository: EventRepository = EventPhysicalRepository(db)
  private val partnerRepository: PartnerRepository = PartnerPhysicalRepository(db)
  
  private val subject = EventService(eventRepository, partnerRepository)

  it should "return exception when is trying create a partner's a event without it is registered" in {
    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))
    val input = CreateEventInput("Knot Fest", "Knot Fest", LocalDateTime.of(2024, 11, 9, 20, 0, 0))
    val either = subject.create(id, input)

    either map { e => e.left.e should be(Left(DomainException(s"the partner $id not exists yet"))) }
  }

  it should "create a event when partner is registered" in {
    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))
    partnerRepository.save(partner)

    val eventDate = LocalDateTime.of(2024, 11, 9, 20, 0, 0)
    val input = CreateEventInput("Knot Fest", "Knot Fest", eventDate)

    for
      e <- subject.create(id, input)
      events <- eventRepository.findByPartnerId(id)
      event = events.last
    yield
      events should have length 1
      events should contain(Event(event.id, "Knot Fest", "Knot Fest", eventDate, 0, 0, false, id, mutable.Set.empty))
  }

  it should "create return a exception when try creating a section without its event not exists" in {
    val eventId = UUID.randomUUID()
    val input = CreateEventSectionInput("VIP", "Knot Fest - VIP Section", 10000, 100, 0)

    subject.createSection(eventId, input) map { e =>
      e.left.e should be(Left(DomainException(s"the event $eventId wasn't created yet")))
    }
  }

  it should "create a section when a event have been created" in {
    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))
    partnerRepository.save(partner)

    val eventDate = LocalDateTime.of(2024, 11, 9, 20, 0, 0)
    val createEventFuture = subject.create(id, CreateEventInput("Knot Fest", "Knot Fest", eventDate))

    whenReady(createEventFuture) { _ =>
      val events = Await.result(eventRepository.findAll(), Duration.Inf)
      val eventSectionInput = CreateEventSectionInput("VIP", "Knot Fest Vip Area", 10000, 20, 0)
      val eventSectionFuture = subject.createSection(events.last.id, eventSectionInput)

      eventually {
        whenReady(eventSectionFuture) { _=>
          val eventOptional = Await.result(eventRepository.findById(events.last.id), Duration.Inf)

          val event = eventOptional.head
          eventOptional should not be empty
          event.partnerId should be(partner.id)
          event.totalSpots should be(20)
          event.totalSpotsReserved should be(0)
        }
      }
    }
  }
}
