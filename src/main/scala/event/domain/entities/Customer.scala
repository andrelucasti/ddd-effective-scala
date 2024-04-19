package io.andrelucas
package event.domain.entities

import common.domain.valueobjects.Email
import common.domain.{AggregateRoot, ValueObject}

import java.time.LocalDate
import java.util.UUID

case class Customer(id: UUID,
                    nationalId: String,
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
             name: String,
             email: String,
             birthDate: LocalDate): Customer =
    Customer(UUID.randomUUID(), nationalId, Name(name), Email(email), birthDate)

// Value Objects
class Name(private val name: String) extends ValueObject[String](name)