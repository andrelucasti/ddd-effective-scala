package io.andrelucas
package partner.domain.domainevents

import common.domain.DomainEvent

import java.time.LocalDateTime
import java.util.UUID

trait PartnerEvent(aggregateRootId: UUID,
                   eventName: String,
                   eventDate: LocalDateTime,
                   version: Int) extends DomainEvent
