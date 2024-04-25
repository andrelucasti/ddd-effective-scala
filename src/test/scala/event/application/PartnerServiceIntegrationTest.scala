package io.andrelucas
package event.application

import common.domain.valueobjects.Name
import event.domain.entities.Partner
import event.domain.repository.PartnerRepository
import event.infra.db.PartnerTable
import event.infra.repositories.PartnerPhysicalRepository

import io.andrelucas.common.domain.DomainException
import slick.jdbc.PostgresProfile.api.*
import slick.lifted.TableQuery

import java.time.LocalDateTime
import java.util.UUID

class PartnerServiceIntegrationTest extends IntegrationSpec {
  private val repository: PartnerRepository = PartnerPhysicalRepository(db)
  private val subject = PartnerService(repository)
  private val partnerTable = TableQuery[PartnerTable]

  before {
    db.run( DBIO.seq(
      sqlu""" DELETE FROM partners """
    ))
  }

  it should "save a partner" in {
    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))

    subject.register(partner)

    val partnerCreated  = repository.findById(id)

    partnerCreated should not be empty
    partnerCreated should contain (partner)
  }

  it should "return exception when is trying create a partner's a event without it registered" in {
    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))
    val input = RegisterEventInput("Knot Fest", "Knot Fest", LocalDateTime.of(2024, 11, 9, 20, 0, 0))

    val either = subject.registerEvent(id, input)
    
    either.left.e should be (Left(DomainException(s"the partner $id not exist")))
  }
}
