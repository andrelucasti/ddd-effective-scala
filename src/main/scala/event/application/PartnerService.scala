package io.andrelucas
package event.application

import event.domain.repository.PartnerRepository

import io.andrelucas.event.domain.entities.Partner

case class PartnerService(private val partnerRepository: PartnerRepository) {

  def save(partner: Partner): Unit =
    partnerRepository.save(partner)
}
