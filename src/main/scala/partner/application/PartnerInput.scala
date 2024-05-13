package io.andrelucas

import com.google.gson.GsonBuilder
import zio.json.*
import zio.schema.{DeriveSchema, Schema}

case class PartnerInput(name: String)

object PartnerInput:
  //given decoder: JsonDecoder[PartnerInput] = DeriveJsonDecoder.gen[PartnerInput]
  given schema: Schema[PartnerInput] = DeriveSchema.gen[PartnerInput]
  
  def fromJson(payload: String): PartnerInput =
    GsonBuilder().create().fromJson(payload, classOf[PartnerInput])