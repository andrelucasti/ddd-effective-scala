package io.andrelucas
package event.domain.entities

import common.domain.Entity

import java.util.UUID

case class EventSpot(id: UUID,
                     location: String,
                     isReserved: Boolean,
                     isPublished: Boolean) extends Entity:

  override def toJSON: Any = ""