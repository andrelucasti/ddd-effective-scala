package io.andrelucas
package event.domain.domainevents

import common.domain.DomainEvent

import org.apache.pekko.actor.typed.Behavior

import java.time.LocalDateTime
import java.util.UUID

case class EventCreated(eventId: UUID) extends EventEvent(eventId, "event-created", LocalDateTime.now(), 1)