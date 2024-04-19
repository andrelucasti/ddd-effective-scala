package io.andrelucas
package common.domain.valueobjects

import common.domain.DomainException

class EmailTest extends UnitSpec {

  it should "throw exception when email not is valid" in {
    assertThrows[DomainException] {
      Email("askldjajdkla")
    }
  }

  it should "return a email when is valid" in {
    val email = Email("andrelucas@gmail.com")
    assert("andrelucas@gmail.com" == email.value)
  }
}
