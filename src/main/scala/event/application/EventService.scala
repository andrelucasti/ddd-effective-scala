package io.andrelucas
package event.application

import event.domain.entities.Event
import event.domain.repository.{EventRepository, PartnerRepository}

import io.andrelucas.common.domain.DomainException

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class EventService(private val eventRepository: EventRepository, 
                        private val partnerRepository: PartnerRepository):
  
  def create(partnerId: UUID,
             input: CreateEventInput): Future[Either[Throwable, Unit]] =

    partnerRepository
      .exists(partnerId)
      .map { p=>
        if !p then Left(DomainException(s"the partner $partnerId not exists yet"))
        else
          Right {
            val event = Event.create(input.eventName, input.eventDescription, input.date, partnerId)
            eventRepository.save(event)
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
                    input: CreateEventSectionInput): Future[Either[Throwable, Unit]] = ???


