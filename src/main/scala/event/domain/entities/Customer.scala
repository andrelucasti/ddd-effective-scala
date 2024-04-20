package io.andrelucas
package event.domain.entities

import common.domain.AggregateRoot
import common.domain.valueobjects.{BrazilNationalId, Email, Name, NationalId, Nationality}

import java.time.LocalDate
import java.util.UUID

case class Customer(id: UUID,
                    nationalId: NationalId,
                    private var pName: Name,
                    private var pEmail: Email,
                    birthDate: LocalDate) extends AggregateRoot:

  def changeName(newName: String): Unit = this.pName = Name(newName)
  def name: String = this.pName.value

  def changeEmail(newEmail: String): Unit = this.pEmail = Email(newEmail)
  def email: String = this.pEmail.value

  override def toJSON: String = ""

object Customer:
  def create(nationalId: String,
             nationality: Nationality,
             name: String,
             email: String,
             birthDate: LocalDate): Customer =

    Customer(UUID.randomUUID(), nationalitiesFactory(nationality, nationalId), Name(name), Email(email), birthDate)

  private val nationalitiesFactory: (Nationality, String) => NationalId = (n: Nationality, value: String) =>
    n match
      case Nationality.BR => BrazilNationalId(value)
      case _ => throw new IllegalArgumentException()

