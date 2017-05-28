package com.yetanotherdevblog.petclinic.repositories

import com.yetanotherdevblog.petclinic.model.PetType
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository interface PetTypeRepository : ReactiveMongoRepository<PetType, String>