package io.andrelucas
package event.application

import com.google.gson.GsonBuilder
import zio.schema.{DeriveSchema, Schema}

case class PartnerInput(name: String)

object PartnerInput:
  given schema: Schema[PartnerInput] = DeriveSchema.gen[PartnerInput]
  
  def fromJson(payload: String): PartnerInput = {
    GsonBuilder().create().fromJson(payload, classOf[PartnerInput])
  }
  
  