package io.andrelucas
package event.application

import common.domain.DomainException
import event.application.inputs.CreateEventInput
import event.domain.entities.Event
import event.domain.repository.EventRepository
import partner.domain.repository.PartnerRepository

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

case class EventService(private val eventRepository: EventRepository, 
                        private val partnerRepository: PartnerRepository):
  
  def create(partnerId: UUID,
             input: CreateEventInput): Future[Try[Unit]] =

  partnerRepository.exists(partnerId).map { partnerExists =>
    Try {
      if partnerExists then

        val event = Event.create(input.eventName, input.eventDescription, input.date, partnerId)
        for
          _ <- eventRepository.save(event)
        yield() 
         
      else throw DomainException(s"the partner $partnerId not exists yet")
    }
  }


  //Registering an onComplete callback on the future ensures that the corresponding closure
  // is invoked after the future is completed, eventually.
  def createSection(eventId: UUID,
                    input: CreateEventSectionInput): Future[Try[Unit]] =

    eventRepository.findById(eventId).map { eventOptional =>
      Try {
        eventOptional match
          case None => throw DomainException(s"the event $eventId wasn't created yet")
          case Some(event) =>
            event.addSection(input.name, input.description, input.totalSpots, input.totalSpotsReserved, input.priceInCents)
            eventRepository.update(event)
      }
    }