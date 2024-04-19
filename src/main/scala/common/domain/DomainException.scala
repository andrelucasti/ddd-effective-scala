package io.andrelucas
package common.domain

case class DomainException(msg: String) extends RuntimeException(msg) {

}
