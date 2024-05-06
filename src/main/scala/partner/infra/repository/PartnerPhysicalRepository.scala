package io.andrelucas
package partner.infra.repository

import common.domain.valueobjects.Name
import event.infra.db.{PartnerEntity, PartnerTable}
import partner.domain.Partner
import partner.domain.repository.PartnerRepository

import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*
import slick.lifted.TableQuery

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class PartnerPhysicalRepository(db: Database) extends PartnerRepository {
  private val partnerTable = TableQuery[PartnerTable]

  override def save(entity: Partner): Future[Unit] =
    val insert = (for {
      _ <- partnerTable += PartnerEntity(entity.id, entity.name)
  
    } yield ()).transactionally

    db.run(insert)

  override def findById(id: UUID): Future[Option[Partner]] =
    val action = partnerTable.filter(_.id === id)
      .result
      .headOption
      .map(_.map(p=> Partner(p.id, Name(p.name))))

    db.run(action)

  override def findAll(): Future[List[Partner]] =
    db.run(partnerTable
      .result
      .map(_
        .map(p => Partner(p.id, Name(p.name))))
      .map(_.toList))


  override def exists(id: UUID): Future[Boolean] =
    db.run(partnerTable.filter(_.id === id).exists.result)

  override def update(entity: Partner): Future[Unit] = ???

  override def delete(id: UUID): Unit = ???
}
