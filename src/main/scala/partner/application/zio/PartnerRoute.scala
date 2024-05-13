package io.andrelucas
package partner.application.zio

import event.application.PartnerInput

import zio.*
import zio.http.*
import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec

object PartnerRoute {
  def apply(): Routes[PartnerZioRepo, Response] = Routes(
    Method.POST / "partners" -> handler { (req: Request) =>
      for {
        input <- req.body.to[PartnerInput].orElseFail(Response.badRequest("Bad Request"))
        r <- PartnerZioRepo.save(input)
          .mapBoth(
            _ => Response.internalServerError(s"Failed to register the partner $input"),
            _ => Response.status(Status.Created)
          )
      } yield r 
    } 
  )
}
