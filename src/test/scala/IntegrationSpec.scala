package io.andrelucas

import org.scalatest.{BeforeAndAfter, Inside, Inspectors, OptionValues, Suite}
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*

abstract class IntegrationSpec extends AsyncFlatSpec
  with BeforeAndAfter
  with OptionValues
  with Inside
  with Inspectors
  with Suite
  with Matchers
  {

  val db = Database.forConfig("ddd")

  before {
    db.run(
      DBIO.seq(
        sqlu"""DELETE FROM event_spots""",
        sqlu"""DELETE FROM event_sections""",
        sqlu"""DELETE FROM events""",
        sqlu"""DELETE FROM partners""",
      )
    )
  }
}
