package io.andrelucas
package partner.domain

import common.domain.AggregateRoot
import common.domain.valueobjects.Name
import event.domain.entities.Event

import io.andrelucas.partner.domain.domainevents.PartnerCreated

import java.time.LocalDateTime
import java.util.UUID

case class Partner(id: UUID,
                   private var pName: Name) extends AggregateRoot:
  
  def createEvent(name: String, description: String, date: LocalDateTime): Event =
    Event.create(name, description, date, this.id)
  
  def name: String = this.pName.value
  
  override def toJSON: Any = ""

object Partner:
  def create(name: String):Partner =
    val partner = Partner(UUID.randomUUID(), Name(name))
    partner.addEvent(PartnerCreated(partner.id))
    
    partner