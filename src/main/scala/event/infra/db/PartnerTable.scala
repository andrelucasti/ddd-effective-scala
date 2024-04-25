package io.andrelucas
package event.infra.db

import slick.jdbc.PostgresProfile.api.*

import java.util.UUID

case class PartnerEntity(id: UUID, 
                   name: String)

class PartnerTable(tag: Tag) extends Table[PartnerEntity](tag, "partners") {
  def id = column[UUID]("id", O.SqlType("UUID"))
  def name = column[String]("name")

  override def * = (id, name) <> ((PartnerEntity.apply _).tupled, PartnerEntity.unapply)
}