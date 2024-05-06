package io.andrelucas
package common.domain

trait DomainEventPublisher():
  def publish(domainEvent: DomainEvent): Unit