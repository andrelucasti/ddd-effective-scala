package io.andrelucas
package partner.application

import partner.domain.Partner
import partner.domain.repository.PartnerRepository

import io.andrelucas.common.application.ApplicationService
import io.andrelucas.common.domain.DomainEventManager
import io.andrelucas.event.application.PartnerInput
import io.andrelucas.partner.domain.domainevents.PartnerEventPublisher

case class PartnerService(private val partnerRepository: PartnerRepository,
                          private val partnerEventPublisher: PartnerEventPublisher) extends ApplicationService(DomainEventManager(partnerEventPublisher)) {

  def register(partner: PartnerInput): Unit =
    val newPartner = Partner.create(partner.name)
    
    partnerRepository.save(newPartner)
    finish(newPartner)
}
