package io.andrelucas
package event.domain.entities

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.mutable

class EventTest extends UnitSpec {

  it should "create a event" in {
    val date = LocalDateTime.of(2024, 2, 2, 12, 0, 0)
    val partnerId = UUID.randomUUID()
    val sections = List.empty
    val eventName = Event.create("EventName", "eventDescription", date, partnerId)

    val eventExpected = Event(eventName.id, "EventName", "eventDescription", date, 0, 0, false, partnerId, mutable.Set.empty)
    assert(eventName == eventExpected)
  }

  it should "sum total spots when a section is created" in {

  }

  it should "sum total spots reserved when a section is created with spots reserved" in {

  }

  it should "create a event section" in {
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
}
