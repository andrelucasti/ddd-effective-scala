package io.andrelucas
package common.domain

import scala.concurrent.Future

trait Repository[A <: AggregateRoot, ID] {
  def save(entity: A): Future[Unit]
  def update(entity: A): Future[Unit]
  def findById(id: ID): Future[Option[A]]
  def findAll(): Future[List[A]]
  def delete(id: ID): Unit
}
