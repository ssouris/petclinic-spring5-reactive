package com.yetanotherdevblog.petclinic.repositories

import com.yetanotherdevblog.petclinic.model.Speciality
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository interface SpecialityRepository : ReactiveMongoRepository<Speciality, String> {

    fun findAllByName(nameList: Iterable<String>) : Flux<Speciality>

}
