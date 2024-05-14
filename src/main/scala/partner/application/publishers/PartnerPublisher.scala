package io.andrelucas
package partner.application

import common.domain.{AggregateRoot, DomainEvent, DomainPublisher}
import partner.domain.domainevents.PartnerEvent

import com.typesafe.scalalogging.Logger
import org.apache.pekko.actor.typed.ActorSystem

trait PartnerPublisher extends DomainPublisher[PartnerEvent]:
  def publish(partnerEvent: PartnerEvent): Unit
  
case class PartnerActorPublisher(actor: ActorSystem[PartnerEvent]) extends PartnerPublisher:
  private val logger = Logger("PartnerActorPublisher")
  override def publish(partnerEvent: PartnerEvent): Unit =
    actor ! partnerEvent

  override def publishEvent(aggregateRoot: AggregateRoot): Unit =
    aggregateRoot.events().foreach { event =>
      logger.info(s"Publishing -> $event")
      publish(event.asInstanceOf)
      logger.info(s"Published -> $event")
    }
    aggregateRoot.clearEvents()


