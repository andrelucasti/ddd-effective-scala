package io.andrelucas
package event.application

import io.andrelucas.common.domain.valueobjects.Name
import io.andrelucas.event.domain.entities.Partner
import io.andrelucas.event.domain.repository.{EventRepository, PartnerRepository}
import io.andrelucas.event.infra.repositories.{EventPhysicalRepository, PartnerPhysicalRepository}
import org.scalatest.matchers.should.Matchers
import org.scalatest.{Inside, Inspectors, OptionValues, Suite}
import slick.jdbc.JdbcBackend.Database

import java.time.LocalDateTime
import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class AsyncTest extends munit.FunSuite with Matchers {


  val db = Database.forConfig("ddd")
  private val eventRepository: EventRepository = EventPhysicalRepository(db)
  private val partnerRepository: PartnerRepository = PartnerPhysicalRepository(db)

  private val subject = EventService(eventRepository, partnerRepository)

  test("asdadad"){
    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))
    partnerRepository.save(partner)

    val eventDate = LocalDateTime.of(2024, 11, 9, 20, 0, 0)
    val eventSectionInput = CreateEventSectionInput("VIP", "Knot Fest Vip Area", 10000, 20, 0)

    Await.result(subject.create(id, CreateEventInput("Knot Fest", "Knot Fest", eventDate)), Duration.Inf)

    val events = Await.result(eventRepository.findAll(), Duration.Inf)
    Await.result(subject.createSection(events.last.id, eventSectionInput), Duration.Inf)

    Thread.sleep(1000)
    val eventOptional = Await.result(eventRepository.findById(events.last.id), Duration.Inf)
    val event = eventOptional.head

    println(event)
    eventOptional should not be empty
    event.partnerId should be(partner.id)
    event.totalSpots should be(20)
    event.totalSpotsReserved should be(0)
  }
}
