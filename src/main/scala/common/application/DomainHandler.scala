package io.andrelucas
package common.application

import com.typesafe.scalalogging.Logger
import io.andrelucas.common.domain.DomainEvent

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait DomainHandler[A]() {
  private val logger = Logger("DomainHandler")
  def consume(): Future[Unit]
}
