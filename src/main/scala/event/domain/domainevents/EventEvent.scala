package io.andrelucas
package event.domain.domainevents

import common.domain.DomainEvent

import java.time.LocalDateTime
import java.util.UUID

trait EventEvent(aggregateRootId: UUID,
                 eventName: String,
                 eventDate: LocalDateTime,
                 version: Int) extends DomainEvent
