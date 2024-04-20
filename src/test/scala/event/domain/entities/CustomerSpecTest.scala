package io.andrelucas
package event.domain.entities

import common.domain.valueobjects.Nationality.{BR, EUA}

import java.time.LocalDate

class CustomerSpecTest extends UnitSpec {

  it should "create a new customer" in {
    val birthDate = LocalDate.of(1994, 2, 2)

    val customer = Customer.create("123456", BR, "Andre", "andrelucastic@gmail.com", birthDate)

    assert("123456" == customer.nationalId.value)
    assert("Andre" == customer.name)
    assert("andrelucastic@gmail.com" == customer.email)
    assert(birthDate == customer.birthDate)
  }

  it should "change name" in {
    val birthDate = LocalDate.of(1994, 2, 2)
    val customer = Customer.create("123456", BR, "Andre", "andrelucastic@gmail.com", birthDate)

    customer.changeName("Lucas")

    assert("Lucas" == customer.name)
  }

  it should "change email" in {
    val birthDate = LocalDate.of(1994, 2, 2)
    val customer = Customer.create("123456", BR, "Andre", "andrelucastic@gmail.com", birthDate)

    customer.changeEmail("lucasandre@gmail.com")

    assert("lucasandre@gmail.com" == customer.email)
  }

  it should "throw exception when nationality doesn't exist" in {
    val birthDate = LocalDate.of(1994, 2, 2)

    assertThrows[IllegalArgumentException]{
      Customer.create("123456", EUA, "Andre", "andrelucastic@gmail.com", birthDate)
    }
  }
}
