package io.andrelucas
package event.application

import common.domain.DomainEvent
import event.infra.db.PartnerTable
import partner.application.PartnerService
import partner.domain.domainevents.{PartnerCreated}
import partner.domain.repository.PartnerRepository
import partner.infra.repository.PartnerPhysicalRepository

import slick.lifted.TableQuery

import java.util.concurrent.LinkedTransferQueue

class PartnerServiceIntegrationTest extends IntegrationSpec {
  private val partnerTable = TableQuery[PartnerTable]
  private val channel = LinkedTransferQueue[PartnerCreated]()
  private val repository: PartnerRepository = PartnerPhysicalRepository(db)
  private val partnerService = PartnerService(repository)

  it should "create a partner" in {
   
    val partnerInput = PartnerInput("Andre Lucas Input")
    partnerService.register(partnerInput)
    val partnerCreated = repository.findAll.futureValue

    partnerCreated should have length 1
  }
}
