package io.andrelucas
package common.domain.valueobjects

import io.andrelucas.common.domain.DomainEvent
import io.andrelucas.event.domain.domainevents.{EventCreated, EventUpdated}
import io.andrelucas.partner.domain.domainevents.PartnerCreated
import ox.*
import ox.channels.*

import java.util.UUID
import scala.concurrent.{Future, blocking}
import scala.concurrent.ExecutionContext.Implicits.global

case object TestChannels:
  val channel: Channel[DomainEvent] = Channel.unlimited

  def main(args: Array[String]): Unit = {
    println(Thread.currentThread().getName)
    channel.send(EventCreated(UUID.randomUUID()))
    Thread.sleep(1000)
    channel.send(EventUpdated(UUID.randomUUID()))
    Thread.sleep(2000)
    channel.send(PartnerCreated(UUID.randomUUID()))

  }
