package io.andrelucas
package event.domain.entities

import common.domain.{AggregateRoot, Entity}

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.mutable

case class Event(id: UUID,
                 name: String,
                 description: String,
                 date: LocalDateTime,
                 totalSpots: Long,
                 totalSpotsReserved: Long,
                 isPublished: Boolean,
                 partnerId: UUID,
                 sections: mutable.Set[EventSection]
                ) extends AggregateRoot{

  def addSection(name: String,
                 description: String,
                 totalSpots: Long,
                 totalSpotsReserved:
                 Long,
                 priceInCents: Long): Unit =

    sections += (EventSection.create(name, description, priceInCents, totalSpots, totalSpotsReserved))



  override def toJSON: Any = ""

}

object Event:

  def create(name: String, description: String, date: LocalDateTime, partnerId: UUID): Event =
    Event(UUID.randomUUID(), name, description, date, 0, 0, false, partnerId, mutable.Set.empty)


case class EventSection(id: UUID,
                        name: String,
                        priceInCents: Long,
                        description: String,
                        totalSpots: Long,
                        totalSpotsReserved: Long,
                        spots: mutable.Set[EventSpot]
                       ) extends Entity:

  override def toJSON: Any = ""

object EventSection:

  def create(name: String, description: String, priceInCents: Long, totalSpots: Long, totalSpotsReserved: Long): EventSection =
    EventSection(UUID.randomUUID(), name, priceInCents, description, totalSpots, totalSpotsReserved, mutable.Set.empty)

case class EventSpot(id: UUID,
                     location: String,
                     isReserved: Boolean,
                     isPublished: Boolean) extends Entity:

  override def toJSON: Any = ""