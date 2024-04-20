package io.andrelucas
package event.domain.entities

import common.domain.AggregateRoot
import common.domain.valueobjects.Name

import java.util.UUID

case class Partner(id: UUID,
                   private var pName: Name) extends AggregateRoot:
  
  def name = this.pName.value
  
  override def toJSON: Any = ""
