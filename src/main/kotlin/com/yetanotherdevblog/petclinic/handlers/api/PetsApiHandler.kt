package com.yetanotherdevblog.petclinic.handlers.api

import com.yetanotherdevblog.petclinic.model.Pet
import com.yetanotherdevblog.petclinic.model.Visit
import com.yetanotherdevblog.petclinic.repositories.PetRepository
import com.yetanotherdevblog.petclinic.repositories.VisitRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class PetsApiHandler(val petRepository: PetRepository,
                     val visitRepository: VisitRepository) {

    fun getPets(serverRequest: ServerRequest) =
            ok().body(petRepository.findAll(), Pet::class.java)

    fun getPet(serverRequest: ServerRequest) =
            ok().body(petRepository.findById(serverRequest.pathVariable("id")), Pet::class.java)

    fun getPetVisits(serverRequest: ServerRequest) =
            ok().body(visitRepository.findByPetId(serverRequest.pathVariable("id")), Visit::class.java)

}