package io.andrelucas
package common.infra

import common.domain.{DomainEvent, EventPublisher}

import com.typesafe.scalalogging.Logger

class DomainEventPublisher extends EventPublisher:
  private val logger = Logger("DomainEventPublisher")
  override def publish(domainEvent: DomainEvent): Unit =
    logger.info(s"Publishing event: $domainEvent")
    
    
