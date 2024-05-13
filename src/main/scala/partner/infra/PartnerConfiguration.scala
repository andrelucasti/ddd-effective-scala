package io.andrelucas
package partner.infra

import common.infra.Configuration
import partner.application.PartnerService
import partner.domain.repository.PartnerRepository
import partner.infra.repository.PartnerPhysicalRepository

object PartnerConfiguration extends Configuration {
  val partnerRepository: PartnerRepository = PartnerPhysicalRepository(db)
  val partnerService: PartnerService = PartnerService(partnerRepository)
}
