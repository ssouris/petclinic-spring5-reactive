package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.petclinic.html
import com.yetanotherdevblog.petclinic.model.PetType
import com.yetanotherdevblog.petclinic.repositories.PetTypeRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class PetTypeHandler(val petTypeRepository: PetTypeRepository) {

    fun goToPetTypeIndex(serverRequest: ServerRequest) = goToIndex()

    fun goToAdd(serverRequest: ServerRequest) = ok().contentType(MediaType.TEXT_HTML).render("petTypes/add")

    fun add(serverRequest: ServerRequest) = serverRequest.body(BodyExtractors.toFormData())
            .flatMap {
                val formData = it.toSingleValueMap()
                petTypeRepository.save(PetType(name = formData["name"]!!))
            }
            .then(goToIndex())

    fun goToEdit(serverRequest: ServerRequest) =
            petTypeRepository.findById(serverRequest.queryParam("id").orElseThrow{ IllegalArgumentException() })
                    .map { mapOf("id" to it.id, "name" to it.name) }
                    .flatMap { ok().html().render("petTypes/edit", it) }

    fun edit(serverRequest: ServerRequest) =
            serverRequest.body(BodyExtractors.toFormData())
                    .flatMap {
                        val formData = it.toSingleValueMap()
                        petTypeRepository.save(PetType(id = formData["id"]!!, name = formData["name"]!!))
                    }
                    .then(goToIndex())

    fun goToIndex() = ok().html().render("petTypes/index", mapOf("petTypes" to petTypeRepository.findAll()))

}
