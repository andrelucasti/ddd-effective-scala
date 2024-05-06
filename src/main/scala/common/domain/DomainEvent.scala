package io.andrelucas
package common.domain

import java.time.LocalDateTime
import java.util.UUID

trait DomainEvent(aggregateRootId: UUID,
                  eventName: String,
                  eventDate: LocalDateTime,
                  version: Int)
