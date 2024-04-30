package io.andrelucas
package event.application

import common.domain.DomainException
import event.domain.repository.{EventRepository, PartnerRepository}

case class EventService(private val eventRepository: EventRepository, 
                        private val partnerRepository: PartnerRepository):
  
  def create(input: CreateEventInput): Either[Throwable, Unit] =
    partnerRepository.findById(input.partnerId) match
      case None => Left(DomainException(s"the partner ${input.partnerId} not exist"))
      case Some(p) => Right {
        val event = p.createEvent(input.eventName, input.eventDescription, input.date)
        eventRepository.save(event)
      }
    

