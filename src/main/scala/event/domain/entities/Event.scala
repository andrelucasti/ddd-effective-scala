package io.andrelucas
package event.domain.entities

import common.domain.AggregateRoot

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.mutable

case class Event(id: UUID,
                 name: String,
                 description: String,
                 date: LocalDateTime,
                 var totalSpots: Long,
                 totalSpotsReserved: Long,
                 isPublished: Boolean,
                 partnerId: UUID,
                 sections: mutable.Set[EventSection]
                ) extends AggregateRoot:

  def addSection(name: String,
                 description: String,
                 totalSpots: Long,
                 totalSpotsReserved: Long,
                 priceInCents: Long): Unit =

    val section = EventSection.create(name, description, priceInCents, totalSpots, totalSpotsReserved)
    section.initSpotRecursive(totalSpots, name.substring(0).toUpperCase)
    sections += (section)
    
    addSpots(totalSpots)


  private def addSpots(newSpots: Long): Unit = this.totalSpots = this.totalSpots + newSpots 
  override def toJSON: Any = ""

object Event:
  def create(name: String, description: String, date: LocalDateTime, partnerId: UUID): Event =
    Event(UUID.randomUUID(), name, description, date, 0, 0, false, partnerId, mutable.Set.empty)