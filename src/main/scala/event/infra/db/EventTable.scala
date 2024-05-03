package io.andrelucas
package event.infra.db

import event.domain.entities.{Event, EventSpot}

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
                       //eventSections: mutable.Set[EventSectionEntity],
                       updatedAt: LocalDateTime):
  def toDomain(eventSections: Seq[EventSectionEntity], eventSpots: Seq[EventSpotEntity]): Event =
    val sections = convertSectionsEntityToSectionsDomain(eventSections, eventSpots)
    Event(this.id, this.name, this.description, this.date, this.totalSpots, this.totalSpotsReserved, this.isPublished, this.partnerId, sections)

class EventTable(tag: Tag) extends Table[EventEntity](tag, "events") {
  def id = column[UUID]("id", O.SqlType("UUID"))
  def name = column[String]("name")
  def description = column[String]("description")
  def date = column[LocalDateTime]("date")
  def totalSpots = column[Long]("total_spots")
  def totalSpotsReserved = column[Long]("total_spots_reserved")
  def isPublished = column[Boolean]("is_published")
  def partnerId = column[UUID]("partner_id", O.SqlType("UUID"))
  def updatedAt = column[LocalDateTime]("updated_at", O.Default(LocalDateTime.now()))

  override def * = (id, name, description, date, totalSpots, totalSpotsReserved, isPublished, partnerId, updatedAt) <> ((EventEntity.apply _).tupled, EventEntity.unapply)
}

case class EventSectionEntity(id: UUID,
                              name: String,
                              description: String,
                              priceInCents: Long,
                              totalSpots: Long,
                              totalSpotsReserved: Long,
                              isPublished: Boolean,
                              eventId: UUID,
                              updatedAt:LocalDateTime) :
  def toDomain(eventSpots: Seq[EventSpotEntity]): EventSection =
    val spots = convertSpotsEntityToSpotDomain(eventSpots)
    EventSection(id, name, priceInCents, description, totalSpots, totalSpotsReserved, spots, isPublished)

object EventSectionEntity:
  def fromDomain(eventSection: EventSection, eventId: UUID): EventSectionEntity =
    EventSectionEntity(eventSection.id, eventSection.name, eventSection.description, eventSection.priceInCents, 
      eventSection.totalSpots, eventSection.totalSpotsReserved, eventSection.isPublished, eventId, LocalDateTime.now())

class EventSectionTable(tag: Tag) extends Table[EventSectionEntity](tag, "event_sections"){
  def id = column[UUID]("id", O.SqlType("UUID"))
  def name = column[String]("name")
  def description = column[String]("description")
  def priceInCents = column[Long]("price_in_cents")
  def totalSpots = column[Long]("total_spots")
  def totalSpotsReserved = column[Long]("total_spots_reserved")
  def isPublished = column[Boolean]("is_published")
  def eventId = column[UUID]("event_id", O.SqlType("UUID"))
  def updatedAt = column[LocalDateTime]("updated_at", O.Default(LocalDateTime.now()))
 // def event = foreignKey("events", eventId, TableQuery[EventTable])(_.id)

  override def * = (id, name, description, priceInCents, totalSpots, totalSpotsReserved, isPublished, eventId, updatedAt) <> ((EventSectionEntity.apply _).tupled, EventSectionEntity.unapply)
}

case class EventSpotEntity(id: UUID,
                           location: String,
                           isPublished: Boolean,
                           isReserved: Boolean,
                           eventSectionId: UUID,
                           updatedAt:LocalDateTime
                          )
class EventSpotTable(tag: Tag) extends Table[EventSpotEntity](tag, "event_spots") {
  def id = column[UUID]("id", O.SqlType("UUID"))
  def location = column[String]("location")
  def isPublished = column[Boolean]("is_published")
  def isReserved = column[Boolean]("is_reserved")
  def eventSectionId = column[UUID]("event_section_id", O.SqlType("UUID"))
  def updatedAt = column[LocalDateTime]("updated_at", O.Default(LocalDateTime.now()))
//  def eventSections = foreignKey("event_sections", eventSectionId, TableQuery[EventSectionTable])(_.id)

  override def * = (id, location, isPublished, isReserved, eventSectionId, updatedAt) <> ((EventSpotEntity.apply _).tupled, EventSpotEntity.unapply)
}

private def convertSectionsEntityToSectionsDomain(eventSections: Seq[EventSectionEntity], 
                                                  eventSpots: Seq[EventSpotEntity]) : mutable.Set[EventSection] =
  val sections = mutable.Set[EventSection]()
  eventSections
    .foreach(sections += _.toDomain(eventSpots))
  sections

private def convertSpotsEntityToSpotDomain(eventSpots: Seq[EventSpotEntity]): mutable.Set[EventSpot] =
  val spots = mutable.Set[EventSpot]()
  eventSpots.map(s => EventSpot(s.id, s.location, s.isReserved, s.isPublished))
    .foreach(spots += _)
  spots
