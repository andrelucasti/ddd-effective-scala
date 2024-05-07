package io.andrelucas
package event.infra

import common.domain.{DomainEvent, DomainPublisher}
import event.domain.domainevents.EventCreated

import ox.channels.Channel

case class EventPublisher(channel: Channel[EventCreated]) extends DomainPublisher:
  override def publish(domainEvent: DomainEvent): Unit =
    domainEvent match
      case eventCreated: EventCreated => channel.send(eventCreated)
