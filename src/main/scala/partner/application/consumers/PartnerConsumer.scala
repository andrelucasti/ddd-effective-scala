package io.andrelucas
package partner.application.consumers

import partner.domain.domainevents.{PartnerCreated, PartnerEvent}

import org.apache.pekko.actor.typed.Behavior
import org.apache.pekko.actor.typed.scaladsl.Behaviors

object PartnerConsumer {
  def apply(): Behavior[PartnerEvent] =
    Behaviors.receive { (ctx, message) =>
      message match
        case PartnerCreated(partnerId) => ctx.log.info(s"Partner Created - $partnerId")
        case _ => ctx.log.info(s"Event not found")
      Behaviors.same
    }
}
