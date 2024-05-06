package io.andrelucas
package event.domain.repository

import common.domain.Repository
import event.domain.entities.Event
import io.andrelucas.partner.domain.Partner

import java.util.UUID
import scala.concurrent.Future

trait EventRepository extends Repository[Event, UUID]:
  def findByPartnerId(id: UUID): Future[List[Event]]
  def exists(id: UUID): Future[Boolean]
