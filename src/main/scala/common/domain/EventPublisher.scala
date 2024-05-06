package io.andrelucas
package common.domain

trait EventPublisher():
  def publish(domainEvent: DomainEvent): Unit