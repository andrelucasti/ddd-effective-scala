package io.andrelucas

import io.andrelucas.partner.application.{PartnerController, PartnerService}
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

object JavalinConfiguration {
  def apply(partnerService: PartnerService): Javalin = {
    val javalin = Javalin.create(config => {
      config.useVirtualThreads = true
      config.router.apiBuilder(()=> {
        path("/index/", () => get(ctx => ctx.result("DDD - Domain Driven Design")))
        path("/partners", () => post(ctx=> ctx.async(() => PartnerController.create(ctx, partnerService))))
      })
    })
    javalin
  }
}
