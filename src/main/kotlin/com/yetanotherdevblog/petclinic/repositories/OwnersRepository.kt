package com.yetanotherdevblog.petclinic.repositories

import com.yetanotherdevblog.petclinic.model.Owner
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository interface OwnersRepository : ReactiveMongoRepository<Owner, String>