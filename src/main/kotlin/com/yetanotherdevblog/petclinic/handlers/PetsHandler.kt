package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.html
import com.yetanotherdevblog.petclinic.model.Pet
import com.yetanotherdevblog.petclinic.repositories.OwnersRepository
import com.yetanotherdevblog.petclinic.repositories.PetRepository
import com.yetanotherdevblog.petclinic.repositories.PetTypeRepository
import com.yetanotherdevblog.toDate
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class PetsHandler(val petRepository: PetRepository,
                  val ownersRepository: OwnersRepository,
                  val petTypeRepository: PetTypeRepository,
                  val ownersHandler: OwnersHandler) {

    fun goToAdd(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ok().html()
                .render("pets/add", mapOf(
                        "owner" to ownersRepository.findById(
                                    serverRequest.queryParam("ownerId").orElseThrow({IllegalArgumentException()})),
                        "petTypes" to petTypeRepository.findAll()
                ))
    }

    fun add(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.body(BodyExtractors.toFormData()).flatMap {
            val formData = it.toSingleValueMap()
            petRepository.save(Pet(
                         name = formData["name"]!!,
                    birthDate = formData["birthDate"]!!.toDate(),
                        owner = formData["ownerId"]!!,
                         type = formData["typeId"]!!))
        }.then(ownersHandler.goToOwnersIndex())
    }

    fun goToEdit(serverRequest: ServerRequest): Mono<ServerResponse> {
        return petRepository.findById(serverRequest.queryParam("id").orElseThrow({IllegalArgumentException()})).map {
            mapOf("pet" to it,
                  "petTypes" to petTypeRepository.findAll(),
                  "owner" to ownersRepository.findById(it.owner))
        }.flatMap { ok().html().render("pets/edit", it) }
    }

    fun edit(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.body(BodyExtractors.toFormData()).flatMap {
            val formData = it.toSingleValueMap()
            petRepository.save(Pet(
                           id = formData["id"]!!,
                         name = formData["name"]!!,
                    birthDate = formData["birthDate"]!!.toDate(),
                        owner = formData["ownerId"]!!,
                         type = formData["typeId"]!!

            ))
        }.then(ownersHandler.goToOwnersIndex())
    }

    fun delete(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ownersHandler.goToOwnersIndex()
    }

}

