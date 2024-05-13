package io.andrelucas
package partner.application

import event.application.PartnerInput
import partner.domain.Partner
import partner.domain.repository.PartnerRepository

import scala.concurrent.Future

case class PartnerService(private val partnerRepository: PartnerRepository){
  def register(partner: PartnerInput): Future[Unit] =
    val newPartner = Partner.create(partner.name)
    partnerRepository.save(newPartner)
}
