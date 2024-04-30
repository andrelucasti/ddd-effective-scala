package io.andrelucas
package event.application

import java.time.LocalDateTime
import java.util.UUID

case class CreateEventInput(eventName: String, eventDescription: String, date: LocalDateTime, partnerId: UUID)
