package io.andrelucas
package partner.domain.repository

import common.domain.Repository
import partner.domain.Partner

import java.util.UUID
import scala.concurrent.Future

trait PartnerRepository extends Repository[Partner, UUID]:
  def exists(id:UUID): Future[Boolean]
