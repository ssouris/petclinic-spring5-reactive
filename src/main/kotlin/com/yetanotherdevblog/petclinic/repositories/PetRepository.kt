package com.yetanotherdevblog.petclinic.repositories

import com.yetanotherdevblog.petclinic.model.Pet
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository interface PetRepository : ReactiveMongoRepository<Pet, String>