package com.yetanotherdevblog.petclinic.repositories

import com.yetanotherdevblog.petclinic.model.Visit
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository interface VisitRepository : ReactiveMongoRepository<Visit, String> {

    fun findByPetId(petId: String) : Flux<Visit>

    fun findAllByPetId(petId: Iterable<String>) : Flux<Visit>

}