package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.html
import com.yetanotherdevblog.petclinic.model.Pet
import com.yetanotherdevblog.petclinic.model.Vet
import com.yetanotherdevblog.petclinic.repositories.OwnersRepository
import com.yetanotherdevblog.petclinic.repositories.SpecialityRepository
import com.yetanotherdevblog.petclinic.repositories.VetRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class PetsHandler(val petRepository: VetRepository, val ownersRepository: OwnersRepository) {

    fun goToPetsIndex(serverRequest: ServerRequest) = goToIndex()

    fun goToAdd(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ok().html()
                .render("pets/add", mapOf(
                        "owner" to
                        ownersRepository.findById(serverRequest.queryParam("id").orElseThrow({IllegalArgumentException()}))
                ))
    }

    fun add(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.body(BodyExtractors.toFormData()).flatMap {
            formData ->
            petRepository.save(Vet(
                    id = UUID.randomUUID().toString(),
                    firstName = formData["firstName"]?.get(0)!!,
                    lastName = formData["lastName"]?.get(0)!!,
                    specialities = formData["specialities"]?.toCollection(HashSet())!!))
        }.then(goToIndex())
    }

    fun goToEdit(serverRequest: ServerRequest): Mono<ServerResponse> {
        return petRepository.findById(serverRequest.queryParam("id").orElseThrow({IllegalArgumentException()})).map {
            mapOf("pet" to it)
        }.flatMap { ok().html().render("pets/edit", it) }
    }

    fun edit(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.body(BodyExtractors.toFormData()).flatMap {
            formData -> Mono.empty<Pet>()
        }.then(goToIndex())
    }

    fun delete(serverRequest: ServerRequest): Mono<ServerResponse> {
        return goToIndex()
    }

    private fun goToIndex(): Mono<ServerResponse> = ok().html()
            .render("pets/index", mapOf("pets" to petRepository.findAll()))

}
