package io.andrelucas
package common.domain.valueobjects

import common.domain.{DomainException, ValueObject}

case class Email(email: String) extends ValueObject[String](email)

object Email:
  def apply(email: String): Email = {
    if !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
      then throw DomainException("Email invalid")
    
    new Email(email)
  }
