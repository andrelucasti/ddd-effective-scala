package io.andrelucas
package common.application

import io.andrelucas.common.domain.{AggregateRoot, DomainEventManager}

abstract class ApplicationService(domainEventPublisher: DomainEventManager):
  def finish(aggregateRoot: AggregateRoot): Unit = {
    domainEventPublisher.publish(aggregateRoot)
    aggregateRoot.clearEvents()
  }
