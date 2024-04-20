package io.andrelucas
package common.domain.valueobjects

import common.domain.{DomainException, ValueObject}

trait NationalId:
  def validate: Boolean
  def value: String

case class BrazilNationalId(cpf: String) extends ValueObject[String](cpf) with NationalId:

  def apply(cpf: String): BrazilNationalId =
    if !validate then 
      throw DomainException("Brazilian CPF is not valid")
    
    BrazilNationalId(cpf)

  override def validate: Boolean = true

