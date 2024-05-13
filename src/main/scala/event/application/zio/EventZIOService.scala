package io.andrelucas
package event.application.zio

import io.andrelucas.event.application.inputs.CreateEventInput
import zio.{Task, ZIO}

import java.util.UUID

trait EventZIOService:
  def create(partnerId: UUID, eventInput: CreateEventInput): Task[Unit]

object EventZIOService:
  def create(partnerId: UUID, eventInput: CreateEventInput): ZIO[EventZIOService, Throwable, Unit] =
    ZIO.serviceWithZIO[EventZIOService](_.create(partnerId, eventInput))  
