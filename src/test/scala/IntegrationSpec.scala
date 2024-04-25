package io.andrelucas

import org.scalatest.BeforeAndAfter
import slick.jdbc.JdbcBackend.Database

abstract class IntegrationSpec extends UnitSpec 
  with BeforeAndAfter {
  
  val db = Database.forConfig("ddd")
}
