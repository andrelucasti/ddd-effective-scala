package io.andrelucas
package event.application

import common.domain.valueobjects.Name
import event.infra.db.PartnerTable

import io.andrelucas.common.domain.DomainEvent
import io.andrelucas.partner.application.PartnerService
import io.andrelucas.partner.domain.Partner
import io.andrelucas.partner.domain.domainevents.PartnerEventPublisher
import io.andrelucas.partner.domain.repository.PartnerRepository
import io.andrelucas.partner.infra.repository.PartnerPhysicalRepository
import slick.lifted.TableQuery

import java.util.UUID
import java.util.concurrent.LinkedTransferQueue

class PartnerServiceIntegrationTest extends IntegrationSpec {
  private val partnerTable = TableQuery[PartnerTable]
  private val channel = LinkedTransferQueue[DomainEvent]()
  private val repository: PartnerRepository = PartnerPhysicalRepository(db)
  private val partnerEventPublisher = PartnerEventPublisher(channel)
  private val partnerService = PartnerService(repository, partnerEventPublisher)
  

  it should "create a partner" in {
   
    val partnerInput = PartnerInput("Andre Lucas Input")
    partnerService.register(partnerInput)
    val partnerCreated = repository.findAll().futureValue

    partnerCreated should have length(1)
  }
}
