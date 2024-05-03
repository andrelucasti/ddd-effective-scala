package io.andrelucas
package common.domain

import scala.concurrent.Future

trait Repository[A <: AggregateRoot, ID] {
  def save(entity: A): Unit
  def update(entity: A): Unit
  def findById(id: ID): Future[A]
  def findAll(): Future[List[A]]
  def delete(id: ID): Unit
}
