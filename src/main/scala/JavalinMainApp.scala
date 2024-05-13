package io.andrelucas

import partner.infra.PartnerConfiguration

import slick.jdbc.JdbcBackend.Database

object JavalinMainApp {
  val db = Database.forConfig("ddd")
  
  @main
  def main(): Unit = {
    JavalinConfiguration(PartnerConfiguration(db).partnerService).start(8080)
  }
}
