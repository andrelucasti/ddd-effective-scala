package io.andrelucas
package common.domain

import scala.collection.mutable

// control the transaction limit
// protects from invariants
trait AggregateRoot extends Entity:
  private val pEvents: mutable.Set[DomainEvent] = new mutable.HashSet[DomainEvent]()

  def addEvent(event: DomainEvent): Unit =
    pEvents += event
    
  def events(): Set[DomainEvent] = pEvents.toSet

  def clearEvents(): Unit =
    pEvents.clear()
