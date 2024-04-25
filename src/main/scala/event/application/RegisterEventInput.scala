package io.andrelucas
package event.application

import java.time.LocalDateTime

case class RegisterEventInput(eventName: String, eventDescription: String, date: LocalDateTime)
