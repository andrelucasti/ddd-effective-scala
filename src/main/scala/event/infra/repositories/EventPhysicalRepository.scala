package io.andrelucas
package event.infra.repositories

import event.domain.entities.Event
import event.domain.repository.EventRepository
import event.infra.db.{EventEntity, EventTable}

import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*
import slick.lifted.TableQuery

import java.time.LocalDateTime
import java.util.UUID
import scala.concurrent.*
import scala.concurrent.duration.*

case class EventPhysicalRepository(db: Database) extends EventRepository:
  private val eventTable = TableQuery[EventTable]

  override def save(entity: Event): Unit =

    val insert = eventTable += EventEntity(entity.id, entity.name, entity.description, entity.date, entity.totalSpots,
      entity.totalSpotsReserved, entity.isPublished, entity.partnerId, LocalDateTime.now(), LocalDateTime.now())

    db.run(insert)

  override def findById(id: UUID): Option[Event] = ???

  override def findAll(): List[Event] = ???

  override def delete(id: UUID): Unit = ???

  override def findByPartnerId(partnerId: UUID): List[Event] =
    val action = eventTable
      .filter(_.partnerId === partnerId)
      .result
   
    Await.result(db.run(action), 2.second)
      .map(e => e.toDomain)
      .toList
    
  
