package io.andrelucas
package common.application

import common.domain.{AggregateRoot, DomainPublisher}

import com.typesafe.scalalogging.Logger

trait ApplicationService(domainPublisher: DomainPublisher):
  private val logger = Logger("ApplicationService")
  def publishEvent(aggregateRoot: AggregateRoot):Unit =
    aggregateRoot.events().foreach{ event =>
      logger.info(s"Publishing -> $event")
      domainPublisher.publish
    }
    aggregateRoot.clearEvents()