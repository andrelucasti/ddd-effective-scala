package io.andrelucas
package event.domain.entities

import common.domain.AggregateRoot
import common.domain.valueobjects.Name

import java.time.LocalDateTime
import java.util.UUID

case class Partner(id: UUID,
                   private var pName: Name) extends AggregateRoot:
  
  def createEvent(name: String, description: String, date: LocalDateTime): Event =
    Event.create(name, description, date, this.id)
  
  def name = this.pName.value
  
  override def toJSON: Any = ""
