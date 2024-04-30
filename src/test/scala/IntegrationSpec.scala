package io.andrelucas

import org.scalatest.BeforeAndAfter
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*

abstract class IntegrationSpec extends UnitSpec with BeforeAndAfter {
  
  val db = Database.forConfig("ddd")

  before {
    db.run(DBIO.seq(
      sqlu""" DELETE FROM partners """,
      sqlu""" DELETE FROM events """
    ))
  }
}
