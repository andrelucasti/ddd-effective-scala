package io.andrelucas
package event.domain.entities

import io.andrelucas.event.domain.domainevents.{EventCreated, EventUpdated}

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.mutable

class EventTest extends UnitSpec {

  it should "create an event" in {
    val date = LocalDateTime.of(2024, 2, 2, 12, 0, 0)
    val partnerId = UUID.randomUUID()
    val sections = List.empty
    val eventName = Event.create("EventName", "eventDescription", date, partnerId)

    val eventExpected = Event(eventName.id, "EventName", "eventDescription", date, 0, 0, false, partnerId, mutable.Set.empty)

    eventName should be (eventExpected)
  }

  it should "register a domain event when a event is created" in {
    val date = LocalDateTime.of(2024, 2, 2, 12, 0, 0)
    val partnerId = UUID.randomUUID()
    val sections = List.empty
    val event = Event.create("EventName", "eventDescription", date, partnerId)

    val eventExpected = Event(event.id, "EventName", "eventDescription", date, 0, 0, false, partnerId, mutable.Set.empty)

    event should be(eventExpected)
    event.events().size should be (1)
    event.events().head should be (EventCreated(event.id))
  }

  it should "sum total spots when a section is created" in {

  }

  it should "sum total spots reserved when a section is created with spots reserved" in {

  }

  it should "create an event section" in {
    val date = LocalDateTime.of(2024, 2, 2, 12, 0, 0)
    val partnerId = UUID.randomUUID()
    val sections = List.empty
    val eventName = Event.create("EventName", "eventDescription", date, partnerId)

    val eventExpected = Event(eventName.id, "EventName", "eventDescription", date, 0, 0, false, partnerId, mutable.Set.empty)
    assert(eventName == eventExpected)

    val spots = 100
    val price = 1500

    eventName.addSection("VIP", "section VIP for EventName", spots, 0, price)

    assert(1 == eventName.sections.size)

    val vipSection = eventName.sections.head

    vipSection.totalSpots should be (100)
    vipSection.spots should have size 100
    vipSection.totalSpotsReserved should be (0)
    vipSection.name should equal ("VIP")
    vipSection.description should equal("section VIP for EventName")
    vipSection.priceInCents should be (1500)
    eventName.totalSpots should be (100)
    eventName.totalSpotsReserved should be (0)
  }

  it should "register events created and updated when a section is added" in {
    val date = LocalDateTime.of(2024, 2, 2, 12, 0, 0)
    val partnerId = UUID.randomUUID()
    val sections = List.empty
    val event = Event.create("EventName", "eventDescription", date, partnerId)

    val eventExpected = Event(event.id, "EventName", "eventDescription", date, 0, 0, false, partnerId, mutable.Set.empty)
    val spots = 100
    val price = 1500

    event.addSection("VIP", "section VIP for EventName", spots, 0, price)

    event.events() should contain(EventCreated(event.id))
    event.events() should contain(EventUpdated(event.id))
  }

  it should "publish a section when an event is published" in {
    val date = LocalDateTime.of(2024, 11, 9, 20, 0, 0)
    val partnerId = UUID.randomUUID()

    val event = Event.create("KnotFest", "Knot Fest in Lisbon", date, partnerId)
    event.addSection("Meet & Greet + VIP", "Meet & Greet + VIP", 10, 0, 20000)
    event.isPublished should be (false)
    event.sections.head.isPublished should be (false)

    event.publish()
    event.isPublished should be (true)
    event.sections.head.isPublished should be(true)
  }

  it should "unpublish a section when an event is unpublished" in {
    val date = LocalDateTime.of(2024, 11, 9, 20, 0, 0)
    val partnerId = UUID.randomUUID()

    val event = Event.create("KnotFest", "Knot Fest in Lisbon", date, partnerId)
    event.addSection("Meet & Greet + VIP", "Meet & Greet + VIP", 10, 0, 20000)
    event.isPublished should be(false)
    event.sections.head.isPublished should be(false)

    event.publish()
    event.isPublished should be(true)
    event.sections.head.isPublished should be(true)

    event.unpublish()
    event.isPublished should be(false)
    event.sections.head.isPublished should be(false)
  }

  it should "publish all section when an event is published" in {
    val date = LocalDateTime.of(2024, 11, 9, 20, 0, 0)
    val partnerId = UUID.randomUUID()

    val event = Event.create("KnotFest", "Knot Fest in Lisbon", date, partnerId)
    event.addSection("General Adm", "General Admission", 10, 0, 8000)
    event.addSection("Meet & Greet + VIP", "Meet & Greet + VIP", 10, 0, 20000)

    event.isPublished should be(false)
    forAll(event.sections.map(_.isPublished)) {
      p => p should be (false)
    }

    event.publish()
    event.isPublished should be(true)
    forAll(event.sections.map(_.isPublished)) {
      p => p should be(true)
    }
  }

  it should "unpublish all section when an event is unpublished" in {
    val date = LocalDateTime.of(2024, 11, 9, 20, 0, 0)
    val partnerId = UUID.randomUUID()

    val event = Event.create("KnotFest", "Knot Fest in Lisbon", date, partnerId)
    event.addSection("General Adm", "General Admission", 10, 0, 8000)
    event.addSection("Meet & Greet + VIP", "Meet & Greet + VIP", 10, 0, 20000)

    event.isPublished should be(false)
    forAll(event.sections.map(_.isPublished)) {
      p => p should be(false)
    }

    event.publish()
    event.isPublished should be(true)
    forAll(event.sections.map(_.isPublished)) {
      p => p should be(true)
    }

    event.unpublish()
    event.isPublished should be(false)
    forAll(event.sections.map(_.isPublished)) {
      p => p should be(false)
    }
  }
}
