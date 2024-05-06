package io.andrelucas
package event.domain.domainevents

import common.domain.{DomainEvent, DomainEventPublisher}

import java.util.concurrent.LinkedTransferQueue

class EventPublisher(channel: LinkedTransferQueue[DomainEvent]) extends DomainEventPublisher {
  override def publish(domainEvent: DomainEvent): Unit = {
    channel.put(domainEvent)
  }
}
