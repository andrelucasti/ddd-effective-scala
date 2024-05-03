package io.andrelucas

import org.scalatest.BeforeAndAfter
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.PostgresProfile.api.*

import java.time.Duration

abstract class IntegrationSpec extends UnitSpec with BeforeAndAfter {
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
    
    Thread.sleep(Duration.ofMillis(500))
  }
}
