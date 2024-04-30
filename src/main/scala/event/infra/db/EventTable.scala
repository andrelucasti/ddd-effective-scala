package io.andrelucas
package event.infra.db

import io.andrelucas.event.domain.entities.Event
import slick.jdbc.PostgresProfile.api.*

import java.time.LocalDateTime
import java.util.UUID
import scala.collection.mutable

case class EventEntity(id: UUID,
                       name: String,
                       description: String,
                       date: LocalDateTime,
                       totalSpots: Long,
                       totalSpotsReserved: Long,
                       isPublished: Boolean,
                       partnerId: UUID,
                       createdAt: LocalDateTime,
                       updatedAt: LocalDateTime):
  def toDomain =
    Event(this.id, this.name, this.description, this.date, this.totalSpots, this.totalSpotsReserved, this.isPublished, this.partnerId, mutable.Set.empty)


class EventTable(tag: Tag) extends Table[EventEntity](tag, "events") {
  def id = column[UUID]("id", O.SqlType("UUID"))
  def name = column[String]("name")
  def description = column[String]("description")
  def date = column[LocalDateTime]("date")
  def totalSpots = column[Long]("total_spots")
  def totalSpotsReserved = column[Long]("total_spots_reserved")
  def isPublished = column[Boolean]("is_published")
  def partnerId = column[UUID]("partner_id", O.SqlType("UUID"))
  def createdAt = column[LocalDateTime]("created_at", O.Default(LocalDateTime.now()))
  def updatedAt = column[LocalDateTime]("updated_at", O.Default(LocalDateTime.now()))

  override def * = (id, name, description, date, totalSpots, totalSpotsReserved, isPublished, partnerId, createdAt, updatedAt) <> ((EventEntity.apply _).tupled, EventEntity.unapply)
}
