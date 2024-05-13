package io.andrelucas

import partner.infra.PartnerConfiguration

object JavalinMainApp {
  @main
  def main(): Unit = {
    JavalinConfiguration(PartnerConfiguration.partnerService).start(8080)
  }
}
