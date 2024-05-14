package io.andrelucas
package common.domain

import com.typesafe.scalalogging.Logger

trait DomainPublisher[T]:
  private val logger = Logger("DomainPublisher")
  
  def publish(domainEvent: T):Unit
  def publishEvent(aggregateRoot: AggregateRoot): Unit
