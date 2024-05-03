package io.andrelucas
package event.infra.repositories

import common.domain.valueobjects.Name
import event.domain.entities.Partner
import event.domain.repository.PartnerRepository
import event.infra.db.{PartnerEntity, PartnerTable}

import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*
import slick.lifted.TableQuery

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import scala.concurrent.{Await, Future, TimeoutException}

case class PartnerPhysicalRepository(db: Database) extends PartnerRepository {
  private val partnerTable = TableQuery[PartnerTable]

  override def save(entity: Partner): Unit = {
    val insert = partnerTable += (PartnerEntity(entity.id, entity.name))
    db.run(insert)
  }

  override def update(entity: Partner): Unit = ???

  override def findById(id: UUID): Option[Partner] =
    val action = partnerTable.filter(_.id === id)
      .result
      .headOption

    val resultFuture: Future[Option[PartnerEntity]] = db.run(action)
    Await.result(resultFuture.map {
      case Some(p) => Some(Partner(p.id, Name(p.name)))
      case None => None
    }.recoverWith{
      case _: java.util.concurrent.TimeoutException => Future.successful(None)
    }, 1.second)

  override def findByMandatory(id: UUID): Partner = findById(id).head

  override def findAll(): List[Partner] =
    val action = partnerTable.result
    val result = Await.result(db.run(action), 2.second)

    result.map(p => Partner(p.id, Name(p.name))).toList

  override def delete(id: UUID): Unit = ???
}
