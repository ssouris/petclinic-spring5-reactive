package com.yetanotherdevblog.petclinic.repositories

import com.yetanotherdevblog.petclinic.model.Visit
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository interface VisitRepository : ReactiveMongoRepository<Visit, String>