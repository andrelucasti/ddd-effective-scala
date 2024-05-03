package io.andrelucas
package event.infra.repositories

import event.domain.entities.Event
import event.domain.repository.EventRepository
import event.infra.db.{EventEntity, EventSectionEntity, EventSectionTable, EventSpotEntity, EventSpotTable, EventTable}

import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*
import slick.lifted.TableQuery

import java.time.LocalDateTime
import java.util.UUID
import scala.concurrent.*
import scala.concurrent.duration.*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

case class EventPhysicalRepository(db: Database) extends EventRepository:
  private val eventTable = TableQuery[EventTable]
  private val sectionTable = TableQuery[EventSectionTable]
  private val spotTable = TableQuery[EventSpotTable]

  override def save(entity: Event): Unit =
    val insert = eventTable += EventEntity(entity.id, entity.name, entity.description, entity.date, entity.totalSpots,
      entity.totalSpotsReserved, entity.isPublished, entity.partnerId, LocalDateTime.now())

    db.run(insert)

  override def update(entity: Event): Unit =
    val action = (for {

      _ <- eventTable.filter(_.id === entity.id)
        .map(e => (e.totalSpots, e.totalSpotsReserved, e.updatedAt))
        .update((entity.totalSpots, entity.totalSpotsReserved, LocalDateTime.now()))

      _ <- sectionTable ++= entity.sections.map(s => EventSectionEntity.fromDomain(s, entity.id)).toSeq
      _ <- spotTable ++= (for {
        sections <- entity.sections.map(section => (section.id, section.spots))
        spotEntity <- sections._2.map(spot => EventSpotEntity(spot.id, spot.location, spot.isPublished, spot.isReserved, sections._1, LocalDateTime.now()))
      } yield spotEntity).toSeq

    } yield ()).transactionally

    db.run(action)

  override def findByMandatory(id: UUID): Event =
    findById(id).head

  override def findById(id: UUID): Option[Event] =
    val actionJoin = for {
      event <- eventTable.filter(_.id === id)
        .result
        .head
        .map( e=> EventEntity(e.id, e.name, e.description, e.date, e.totalSpots, e.totalSpotsReserved,
          e.isPublished, e.partnerId, e.updatedAt))

      sections <- sectionTable
        .filter(_.eventId === id)
        .result
        .map(_.map(es => EventSectionEntity(es.id, es.name, es.description, es.priceInCents, es.totalSpots,
          es.totalSpotsReserved, es.isPublished, id, es.updatedAt)))

      spots <- DBIO.sequence(
        sections.map(section => {
          spotTable.filter(_.eventSectionId === section.id).result.map(_.map(spot =>
            EventSpotEntity(spot.id, spot.location, spot.isPublished, spot.isReserved, section.id, spot.updatedAt)))
        })
      ).map(_.flatten)

      eventDomain = event.toDomain(sections, spots)
    } yield Some(eventDomain)

    val result = db.run(actionJoin.transactionally)

    Await.result(result, 1.second)

  override def findAll(): List[Event] = ???

  override def delete(id: UUID): Unit = ???

  override def findByPartnerId(partnerId: UUID): List[Event] =
    val action = eventTable
      .filter(_.partnerId === partnerId)
      .result

    Await.result(db.run(action), 2.second)
      .map(e => e.toDomain(Seq.empty, Seq.empty))
      .toList

  override def exists(id: UUID): Boolean =
    val action = db.run(eventTable.filter(_.id === id).exists.result.transactionally)

    val futureResult = Future{
      Await.result(action, 1.second)
    }.recover{
      case _: java.util.concurrent.TimeoutException => false
    }
    
    Await.result(futureResult, Duration.Inf)