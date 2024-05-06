package io.andrelucas
package partner.domain.domainevents

import common.domain.{DomainEvent, DomainEventPublisher}

import java.util.concurrent.LinkedTransferQueue

class PartnerEventPublisher(channel: LinkedTransferQueue[DomainEvent]) extends DomainEventPublisher {
  override def publish(domainEvent: DomainEvent): Unit =
    
    channel.put(domainEvent)
}
