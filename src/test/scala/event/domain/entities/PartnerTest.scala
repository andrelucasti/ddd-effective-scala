package io.andrelucas
package event.domain.entities

import io.andrelucas.common.domain.valueobjects.Name

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.mutable

class PartnerTest extends UnitSpec {

  it should "create a event" in {
    val date = LocalDateTime.of(2024, 6, 24, 12, 0, 0)
    val partner = Partner(UUID.randomUUID(), Name("Andre"))
    val eventCreatedByPartner = partner.createEvent("Rock in Rio", "Rock in Rio Lisboa", date)

    val eventExpected = Event(eventCreatedByPartner.id, "Rock in Rio", "Rock in Rio Lisboa", date, 0, 0, false, partner.id, mutable.Set.empty)

    eventCreatedByPartner should equal (eventExpected)

  }
}
