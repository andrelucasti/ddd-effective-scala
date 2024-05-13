package io.andrelucas
package partner.application

import io.javalin.http.Context
import scala.concurrent.Future

class PartnerController

object PartnerController {
  def create(ctx: Context, partnerService: PartnerService): Future[Unit] =
    val input = PartnerInput.fromJson(ctx.body())

    partnerService.register(input)
}
