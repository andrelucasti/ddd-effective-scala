package io.andrelucas

import org.scalatest.{Inside, Inspectors, OptionValues}
import org.scalatest.flatspec.AnyFlatSpec

abstract class UnitSpec extends AnyFlatSpec
  with OptionValues
  with Inside
  with Inspectors