package io.andrelucas

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.*
import org.scalatest.{Inside, Inspectors, OptionValues, Suite}

abstract class UnitSpec extends AnyFlatSpec
  with OptionValues
  with Inside
  with Inspectors
  with Suite
  with Matchers