package io.andrelucas
package event.domain.repository

import common.domain.Repository
import event.domain.entities.Partner

import java.util.UUID
import scala.concurrent.Future

trait PartnerRepository extends Repository[Partner, UUID]:
  def exists(id:UUID): Future[Boolean]
