package io.andrelucas
package partner.infra

import partner.application.PartnerService
import partner.domain.repository.PartnerRepository
import partner.infra.repository.PartnerPhysicalRepository

import slick.jdbc.JdbcBackend.Database

private class PartnerConfiguration(service: PartnerService):
  val partnerService: PartnerService = service;

case object PartnerConfiguration {
  def apply(db: Database): PartnerConfiguration = {
    val partnerRepository: PartnerRepository = PartnerPhysicalRepository(db)
    val partnerService: PartnerService = PartnerService(partnerRepository)
    
    new PartnerConfiguration(partnerService)
  }
}
