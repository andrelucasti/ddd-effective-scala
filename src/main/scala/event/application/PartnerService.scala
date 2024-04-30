package io.andrelucas
package event.application

import event.domain.entities.Partner
import event.domain.repository.PartnerRepository

case class PartnerService(private val partnerRepository: PartnerRepository) {

  def register(partner: Partner): Unit =
    partnerRepository.save(partner)
}
