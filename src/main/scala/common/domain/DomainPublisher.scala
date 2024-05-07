package io.andrelucas
package common.domain

trait DomainPublisher:
  def publish(domainEvent: DomainEvent):Unit
