package io.andrelucas
package event.application

import common.domain.valueobjects.Name
import event.domain.entities.Partner
import event.domain.repository.PartnerRepository
import event.infra.repositories.PartnerPhysicalRepository

import slick.jdbc.JdbcBackend.Database

import java.util.UUID

class PartnerServiceTest extends UnitSpec {

  private val db = Database.forConfig("ddd")
  private val repository: PartnerRepository = PartnerPhysicalRepository(db)
  private val subject = PartnerService(repository)

  it should "save a partner" in {

    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))

    subject.save(partner)

    val partnerCreated  = repository.findById(id)

    partnerCreated should not be empty
    partnerCreated should contain (partner)
  }
}
