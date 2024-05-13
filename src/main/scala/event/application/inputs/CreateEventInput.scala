package io.andrelucas.event.application.inputs

import zio.schema.{DeriveSchema, Schema}

import java.time.LocalDateTime
import java.util.UUID


case class CreateEventInput(eventName: String, eventDescription: String, date: LocalDateTime)
object CreateEventInput:
  given schema: Schema[CreateEventInput] = DeriveSchema.gen[CreateEventInput]