package io.andrelucas
package event.application

import common.domain.valueobjects.Name
import event.domain.entities.Partner
import event.domain.repository.PartnerRepository
import event.infra.db.PartnerTable
import event.infra.repositories.PartnerPhysicalRepository

import org.scalamock.scalatest.MockFactory
import slick.lifted.TableQuery

import java.util.UUID

class PartnerServiceIntegrationTest extends IntegrationSpec with MockFactory {
  private val repository: PartnerRepository = PartnerPhysicalRepository(db)

  private val partnerTable = TableQuery[PartnerTable]
  
  it should "save a partner" in {
    val id = UUID.randomUUID()
    val partner = Partner(id, Name("Andre Lucas"))
    val subject = PartnerService(repository)

    subject.register(partner)

    val partnerCreated  = repository.findById(id)

    partnerCreated should not be empty
    partnerCreated should contain (partner)
  }
}
