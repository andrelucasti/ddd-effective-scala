package io.andrelucas
package common.application

import common.domain.{AggregateRoot, DomainPublisher}

trait ApplicationService(domainPublisher: DomainPublisher):
  def publishEvent(aggregateRoot: AggregateRoot):Unit =
    aggregateRoot.events().foreach(domainPublisher.publish)
    aggregateRoot.clearEvents()