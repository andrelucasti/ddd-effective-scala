package io.andrelucas
package partner.domain.domainevents

import common.domain.DomainEvent

import java.time.LocalDateTime
import java.util.UUID

case class PartnerCreated(partnerId: UUID) extends DomainEvent(partnerId, "partner-created", LocalDateTime.now(), 1)
