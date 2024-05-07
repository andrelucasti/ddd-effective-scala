package io.andrelucas
package partner.application

import event.application.PartnerInput
import partner.domain.Partner
import partner.domain.repository.PartnerRepository

case class PartnerService(private val partnerRepository: PartnerRepository){

  def register(partner: PartnerInput): Unit =
    val newPartner = Partner.create(partner.name)
    
    partnerRepository.save(newPartner)
}
