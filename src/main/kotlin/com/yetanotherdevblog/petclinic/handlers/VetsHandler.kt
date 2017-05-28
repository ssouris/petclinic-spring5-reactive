package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.petclinic.model.Speciality
import com.yetanotherdevblog.petclinic.model.Vet
import com.yetanotherdevblog.petclinic.repositories.SpecialityRepository
import com.yetanotherdevblog.petclinic.repositories.VetRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class VetsHandler(val vetRepository: VetRepository, val specialityRepository: SpecialityRepository) {

    fun goToVetsIndex(serverRequest: ServerRequest) = goToIndex()

    fun goToAdd(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ok().contentType(MediaType.TEXT_HTML)
                .render("vets/add")
    }

    fun add(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.body(BodyExtractors.toFormData()).flatMap {
            val formData = it.toSingleValueMap()
            vetRepository.save(Vet(
                    id = UUID.randomUUID().toString(),
                    firstName = formData["firstName"]!!,
                    lastName = formData["lastName"]!!,
                    specialities = emptySet<Speciality>()))
        }.then(goToIndex())
    }

    fun goToEdit(serverRequest: ServerRequest): Mono<ServerResponse> {
        return vetRepository.findById(serverRequest.queryParam("id").orElse("")).map {
            mapOf("vet" to it,
                  "specialities" to specialityRepository.findAll())
        }.flatMap { ok().contentType(MediaType.TEXT_HTML).render("vets/edit", it) }
    }

    fun edit(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.body(BodyExtractors.toFormData()).flatMap {
            val formData = it.toSingleValueMap()
            vetRepository.save(Vet(
                    id = formData["id"]!!,
                    firstName = formData["firstName"]!!,
                    lastName = formData["lastName"]!!,
                    specialities = emptySet<Speciality>()))
        }.then(goToIndex())
    }

    fun delete(serverRequest: ServerRequest): Mono<ServerResponse> {
        return goToIndex()
    }

    private fun goToIndex(): Mono<ServerResponse> = ok().contentType(MediaType.TEXT_HTML)
            .render("vets/index", mapOf("vets" to vetRepository.findAll()))

}