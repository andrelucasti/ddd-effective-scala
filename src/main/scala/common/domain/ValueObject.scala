package io.andrelucas
package common.domain


abstract class ValueObject[A](any: A):
  def value: A = any