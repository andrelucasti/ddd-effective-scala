package io.andrelucas
package event.application

case class CreateEventSectionInput(name: String,
                                   description: String,
                                   priceInCents: Long,
                                   totalSpots: Long,
                                   totalSpotsReserved: Long)
