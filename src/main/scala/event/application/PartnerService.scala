package io.andrelucas
package event.application

import common.domain.DomainException
import event.domain.entities.Partner
import event.domain.repository.PartnerRepository

import java.util.UUID

case class PartnerService(private val partnerRepository: PartnerRepository) {

  def register(partner: Partner): Unit =
    partnerRepository.save(partner)
    
  
  def registerEvent(id: UUID, 
                    registerEventInput: RegisterEventInput): Either[Throwable, Unit] = {

    partnerRepository.findById(id) match
      case None => Left(DomainException(s"the partner $id not exist"))
      case Some(p) => Right {
        val event = p.createEvent(registerEventInput.eventName, registerEventInput.eventDescription, registerEventInput.date)
        
        
      }
  }
}
