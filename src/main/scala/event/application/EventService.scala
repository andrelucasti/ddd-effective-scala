package io.andrelucas
package event.application

import common.application.ApplicationService
import common.domain.{DomainEvent, DomainEventManager, DomainException}
import event.domain.domainevents.EventPublisher
import event.domain.entities.Event
import event.domain.repository.EventRepository
import io.andrelucas.partner.domain.repository.PartnerRepository

import java.util.UUID
import java.util.concurrent.LinkedTransferQueue
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

case class EventService(private val eventRepository: EventRepository, 
                        private val partnerRepository: PartnerRepository,
                        private val channel: LinkedTransferQueue[DomainEvent]) 
  
  extends ApplicationService(
    DomainEventManager(
      EventPublisher(channel)
  )
):
  
  def create(partnerId: UUID,
             input: CreateEventInput): Future[Try[Unit]] =

  partnerRepository.exists(partnerId).map { partnerExists =>
    Try {
      if partnerExists then

        val event = Event.create(input.eventName, input.eventDescription, input.date, partnerId)
        for
          _ <- eventRepository.save(event)
        yield
          finish(event)

      else throw DomainException(s"the partner $partnerId not exists yet")
    }
  }


    //closure is only called if the future is completed successfully.
//    partnerRepository.findById(partnerId).foreach { _ =>
//      val event = Event.create(input.eventName, input.eventDescription, input.date, partnerId)
//      eventRepository.save(event)
//    }


//      for {
//        partnerExists <- partnerRepository.exists(partnerId)
//        if partnerExists
//      } yield
//        val event = Event.create(input.eventName, input.eventDescription, input.date, partnerId)
//        eventRepository.save(event)
//



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
            finish(event)
      }
    }