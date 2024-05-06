package io.andrelucas
package event.domain.domainevents

import common.domain.DomainEvent

import java.time.LocalDateTime
import java.util.UUID

case class EventUpdated(eventId: UUID) extends DomainEvent(eventId, "event-updated", LocalDateTime.now(), 1)
