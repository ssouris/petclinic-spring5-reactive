package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.petclinic.html
import com.yetanotherdevblog.petclinic.model.Speciality
import com.yetanotherdevblog.petclinic.repositories.SpecialityRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class SpecialitiesHandler(val specialityRepository: SpecialityRepository) {

    fun goToSpecialitiesIndex(serverRequest: ServerRequest) = goToIndex()

    fun goToAdd(serverRequest: ServerRequest): Mono<ServerResponse> {
        return ok().html()
                .render("specialities/add")
    }

    fun add(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.body(BodyExtractors.toFormData()).flatMap {
            val formData = it.toSingleValueMap()
            specialityRepository.save(Speciality(
                    id = UUID.randomUUID().toString(),
                    name = formData["name"]!!))
        }.then(goToIndex())
    }

    fun goToEdit(serverRequest: ServerRequest): Mono<ServerResponse> {
        return specialityRepository.findById(serverRequest.queryParam("id").orElse("")).map {
            mapOf("id" to it.id, "name" to it.name)
        }.flatMap { ok().html().render("specialities/edit", it) }
    }

    fun edit(serverRequest: ServerRequest): Mono<ServerResponse> {
        return serverRequest.body(BodyExtractors.toFormData()).flatMap {
            val formData = it.toSingleValueMap()
            specialityRepository.save(Speciality(
                    id = formData["id"]!!,
                    name = formData["name"]!!))
        }.then(goToIndex())
    }

    private fun goToIndex() = ok().html()
            .render("specialities/index", mapOf("specialities" to specialityRepository.findAll()))

}
