package io.andrelucas
package partner.application

import partner.domain.Partner
import partner.domain.repository.PartnerRepository
import io.andrelucas.common.domain.DomainPublisher

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class PartnerService(private val partnerRepository: PartnerRepository,
                          private val partnerPublisher: PartnerPublisher):
  
  def register(partner: PartnerInput): Future[Unit] =
    val newPartner = Partner.create(partner.name)
    partnerRepository.save(newPartner).map { _ =>
      partnerPublisher.publishEvent(newPartner)
    }

