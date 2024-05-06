package io.andrelucas

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AsyncFlatSpecLike
import org.scalatest.matchers.should.Matchers
import org.scalatest.*
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*

abstract class IntegrationSpec extends AsyncFlatSpecLike
  with BeforeAndAfter
  with OptionValues
  with Inside
  with Inspectors
  with Suite
  with Matchers
  with ScalaFutures
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
