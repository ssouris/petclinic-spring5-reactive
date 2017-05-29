package com.yetanotherdevblog.petclinic.repositories

import com.yetanotherdevblog.petclinic.model.Pet
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository interface PetRepository : ReactiveMongoRepository<Pet, String> {

    fun findAllByOwner(id: String): Flux<Pet>

}
