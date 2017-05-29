package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.petclinic.html
import com.yetanotherdevblog.petclinic.model.Speciality
import com.yetanotherdevblog.petclinic.repositories.SpecialityRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.util.UUID

@Component
class SpecialitiesHandler(val specialityRepository: SpecialityRepository) {

    fun goToSpecialitiesIndex(serverRequest: ServerRequest) = goToIndex()

    fun goToAdd(serverRequest: ServerRequest) = ok().html().render("specialities/add")

    fun add(serverRequest: ServerRequest) =
            serverRequest.body(BodyExtractors.toFormData())
                    .flatMap {
                        val formData = it.toSingleValueMap()
                        specialityRepository.save(Speciality(
                                id = UUID.randomUUID().toString(), name = formData["name"]!!))
                    }
                    .then(goToIndex())

    fun goToEdit(serverRequest: ServerRequest) =
            specialityRepository.findById(
                    serverRequest.queryParam("id").orElseThrow {IllegalArgumentException()})
                    .map { mapOf("id" to it.id, "name" to it.name) }
                    .flatMap { ok().html().render("specialities/edit", it) }

    fun edit(serverRequest: ServerRequest) =
            serverRequest.body(BodyExtractors.toFormData())
                    .flatMap {
                        val formData = it.toSingleValueMap()
                        specialityRepository.save(Speciality(
                                id = formData["id"]!!,
                                name = formData["name"]!!))
                    }
                    .then(goToIndex())

    fun goToIndex() = ok().html().render("specialities/index",
            mapOf("specialities" to specialityRepository.findAll()))

}
