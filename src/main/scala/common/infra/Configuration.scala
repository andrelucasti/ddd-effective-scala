package io.andrelucas
package common.infra

import slick.jdbc.JdbcBackend.Database

trait Configuration:
  val db = Database.forConfig("ddd")
