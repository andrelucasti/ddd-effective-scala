package io.andrelucas
package event.application.zio

import event.application.EventService
import event.application.inputs.CreateEventInput
import event.infra.EventConfiguration

import zio.{Task, ZIO, ZLayer}

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

case class EventZIOServiceImp(private val eventService: EventService) extends EventZIOService {
  override def create(partnerId: UUID, eventInput: CreateEventInput): Task[Unit] =
    ZIO.fromFuture{ ec =>
      eventService.create(partnerId, eventInput).map {
        case Success(value) => Future.successful(value)
        case Failure(exception) => throw Exception(exception)
      }
    }

  def layer: ZLayer[Any, Nothing, EventZIOServiceImp] = ZLayer {
    ZIO.succeed(EventZIOServiceImp(eventService))
  }
  
}
