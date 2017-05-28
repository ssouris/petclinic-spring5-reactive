package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.petclinic.model.PetType
import com.yetanotherdevblog.petclinic.repositories.PetTypeRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class PetTypeHandler(val petTypeRepository: PetTypeRepository) {

    fun goToPetTypeIndex(serverRequest: ServerRequest) = goToIndex()

    fun goToAdd(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ok().contentType(MediaType.TEXT_HTML).render("petTypes/add")
    }

    fun add(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.body(BodyExtractors.toFormData()).flatMap {
            val formData = it.toSingleValueMap()
            petTypeRepository.save(PetType(name = formData["name"]!!))
        }.then(goToIndex())
    }

    fun goToEdit(serverRequest: ServerRequest): Mono<ServerResponse> {
        return petTypeRepository.findById(serverRequest.queryParam("id").orElse("")).map {
            mapOf("id" to it.id, "name" to it.name)
        }.flatMap { ok().contentType(MediaType.TEXT_HTML).render("petTypes/edit", it) }
    }

    fun edit(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.body(BodyExtractors.toFormData()).flatMap {
            val formData = it.toSingleValueMap()
            petTypeRepository.save(PetType(id = formData["id"]!!, name = formData["name"]!!))
        }.then(goToIndex())
    }

    fun delete(serverRequest: ServerRequest): Mono<ServerResponse> {
        return goToIndex()
    }

    private fun goToIndex(): Mono<ServerResponse> = ok().contentType(MediaType.TEXT_HTML)
            .render("petTypes/index", mapOf("petTypes" to petTypeRepository.findAll()))

}