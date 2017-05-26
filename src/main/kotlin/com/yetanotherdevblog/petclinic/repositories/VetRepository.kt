package com.yetanotherdevblog.petclinic.repositories

import com.yetanotherdevblog.petclinic.model.Vet
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository interface VetRepository : ReactiveMongoRepository<Vet, String>