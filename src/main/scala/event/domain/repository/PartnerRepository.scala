package io.andrelucas
package event.domain.repository

import common.domain.Repository
import event.domain.entities.Partner

import java.util.UUID

trait PartnerRepository extends Repository[Partner, UUID]
