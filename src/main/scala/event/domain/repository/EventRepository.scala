package io.andrelucas
package event.domain.repository

import common.domain.Repository
import event.domain.entities.{Event, Partner}

import java.util.UUID

trait EventRepository extends Repository[Event, UUID]:
  def findByPartnerId(id: UUID): List[Event]
