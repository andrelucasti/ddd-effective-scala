package io.andrelucas
package common.domain

import com.typesafe.scalalogging.Logger

class DomainEventManager(eventPublisher: DomainEventPublisher) {
  private val logger = Logger("DomainEventManager")
  def publish(aggregateRoot: AggregateRoot): Unit =
    aggregateRoot.events().foreach{ domainEvent =>
      eventPublisher.publish(domainEvent)
      logger.info(s"Published -> $domainEvent")
    }
}
