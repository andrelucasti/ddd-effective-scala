package io.andrelucas
package event.domain.domainevents

import common.domain.DomainEvent

import java.time.LocalDateTime
import java.util.UUID

case class EventCreated(eventId: UUID) extends DomainEvent(eventId, "event-created", LocalDateTime.now(), 1)