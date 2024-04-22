package io.andrelucas
package event.domain.entities

import io.andrelucas.common.domain.valueobjects.Name
import org.scalatest.matchers.must.Matchers.not

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.mutable

class PartnerTest extends UnitSpec {

  it should "create an event" in {
    val date = LocalDateTime.of(2024, 6, 24, 12, 0, 0)
    val partner = Partner(UUID.randomUUID(), Name("Andre"))
    val eventCreatedByPartner = partner.createEvent("Rock in Rio", "Rock in Rio Lisboa", date)

    val eventExpected = Event(eventCreatedByPartner.id, "Rock in Rio", "Rock in Rio Lisboa", date, 0, 0, false, partner.id, mutable.Set.empty)

    eventCreatedByPartner should equal (eventExpected)

  }

  it should "publish an event" in {
    val date = LocalDateTime.of(2024, 6, 24, 12, 0, 0)
    val partner = Partner(UUID.randomUUID(), Name("Andre"))
    val eventCreatedByPartner = partner.createEvent("Rock in Rio", "Rock in Rio Lisboa", date)

    eventCreatedByPartner.isPublished should equal(false)

    eventCreatedByPartner.publish()

    eventCreatedByPartner.isPublished should equal(true)

  }

  it should "unpublish an event" in {
    val date = LocalDateTime.of(2024, 6, 24, 12, 0, 0)
    val partner = Partner(UUID.randomUUID(), Name("Andre"))
    val eventCreatedByPartner = partner.createEvent("Rock in Rio", "Rock in Rio Lisboa", date)

    eventCreatedByPartner.isPublished should equal(false)
    eventCreatedByPartner.publish()
    eventCreatedByPartner.isPublished should equal(true)
    eventCreatedByPartner.unpublish()
    eventCreatedByPartner.isPublished should equal(false)

  }
}
