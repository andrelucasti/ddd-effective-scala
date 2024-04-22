package io.andrelucas

import common.domain.Entity
import event.domain.entities.EventSpot

import java.util.UUID
import scala.annotation.tailrec
import scala.collection.mutable


case class EventSection(id: UUID,
                        name: String,
                        priceInCents: Long,
                        description: String,
                        totalSpots: Long,
                        totalSpotsReserved: Long,
                        spots: mutable.Set[EventSpot],
                        private var pIsPublished: Boolean,
                       ) extends Entity:
  
  def isPublished: Boolean = this.pIsPublished
  
  @tailrec
  final def initSpotRecursive(spotQuantity: Long, location: String): Unit =
    if spotQuantity > 0 then
      val spot = EventSpot(UUID.randomUUID(), location + spotQuantity, false, false)
      this.spots += spot
      initSpotRecursive(spotQuantity = spotQuantity - 1, location)
  
  def publish():Unit = this.pIsPublished = true
  def unpublish(): Unit = this.pIsPublished = false
  
  override def toJSON: Any = ""

object EventSection:

  def create(name: String,
             description: String,
             priceInCents: Long,
             totalSpots: Long,
             totalSpotsReserved: Long): EventSection =

    EventSection(UUID.randomUUID(), name, priceInCents, description, totalSpots, totalSpotsReserved, mutable.Set.empty, false)


