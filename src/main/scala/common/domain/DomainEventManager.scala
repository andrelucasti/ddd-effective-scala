package io.andrelucas
package common.domain

class DomainEventManager(eventPublisher: EventPublisher) {
  def publish(aggregateRoot: AggregateRoot): Unit =
    aggregateRoot.events().foreach(eventPublisher.publish)
}
