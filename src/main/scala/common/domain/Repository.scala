package io.andrelucas
package common.domain

trait Repository[A <: AggregateRoot, ID] {
  def save(entity: A): Unit
  def update(entity: A): Unit
  def findById(id: ID): Option[A]
  def findByMandatory(id: ID): A
  def findAll(): List[A]
  def delete(id: ID): Unit
}
