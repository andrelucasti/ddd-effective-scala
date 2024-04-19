package io.andrelucas
package event.domain.entities

import java.time.LocalDate

class CustomerSpecTest extends UnitSpec {

  it should "create a new customer" in {
    val birthDate = LocalDate.of(1994, 2, 2)

    val customer = Customer.create("123456", "Andre", "andrelucastic@gmail.com", birthDate)

    assert("123456" == customer.nationalId)
    assert("Andre" == customer.name)
    assert("andrelucastic@gmail.com" == customer.email)
    assert(birthDate == customer.birthDate)
  }

  it should "change name" in {
    val birthDate = LocalDate.of(1994, 2, 2)
    val customer = Customer.create("123456", "Andre", "andrelucastic@gmail.com", birthDate)

    customer.changeName("Lucas")

    assert("Lucas" == customer.name)
  }

  it should "change email" in {
    val birthDate = LocalDate.of(1994, 2, 2)
    val customer = Customer.create("123456", "Andre", "andrelucastic@gmail.com", birthDate)

    customer.changeEmail("lucasandre@gmail.com")

    assert("lucasandre@gmail.com" == customer.email)
  }
}
