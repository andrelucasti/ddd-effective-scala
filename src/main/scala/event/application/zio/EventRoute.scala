package io.andrelucas
package event.application.zio

import event.application.inputs.CreateEventInput

import zio.*
import zio.http.*
import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec

import java.util.UUID

object EventRoute {
  
  def apply(): Routes[EventZIOService, Response] = Routes(
    Method.POST / "events" / string("partnerId") -> handler { (partnerId: String, req: Request) =>
      for {
        input <- req.body.to[CreateEventInput].orElseFail(Response.badRequest("Bad Request"))
        r <- EventZIOService.create(UUID.fromString(partnerId), input)
          .mapBoth(
            ex => Response.internalServerError(s"Failed to register the event ${input.eventName} - ${ex.getMessage}"),
            _ => Response.status(Status.Created)
          )
      } yield r
    }
  )
}
