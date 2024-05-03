package io.andrelucas
package event.application

import common.domain.DomainException
import event.domain.repository.{EventRepository, PartnerRepository}

import java.util.UUID

case class EventService(private val eventRepository: EventRepository, 
                        private val partnerRepository: PartnerRepository):
  
  def create(input: CreateEventInput): Either[Throwable, Unit] =
    partnerRepository.findById(input.partnerId) match
      case None => Left(DomainException(s"the partner ${input.partnerId} not exist"))
      case Some(p) => Right {
        val event = p.createEvent(input.eventName, input.eventDescription, input.date)
        eventRepository.save(event)
      }

  def createSection(eventId: UUID,
                    input: CreateEventSectionInput): Either[Throwable, Unit] =
    if eventRepository.exists(eventId) then 
     Right {
       val event = eventRepository.findByMandatory(eventId)
       event.addSection(input.name, input.description, input.totalSpots, input.totalSpotsReserved, input.priceInCents)
       eventRepository.update(event)
     }
    else Left(DomainException(s"the event $eventId wasn't created"))

    

