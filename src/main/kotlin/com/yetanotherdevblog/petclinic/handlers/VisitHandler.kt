package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.petclinic.html
import com.yetanotherdevblog.petclinic.model.Visit
import com.yetanotherdevblog.petclinic.repositories.OwnersRepository
import com.yetanotherdevblog.petclinic.repositories.PetRepository
import com.yetanotherdevblog.petclinic.repositories.VisitRepository
import com.yetanotherdevblog.petclinic.toDate
import com.yetanotherdevblog.petclinic.toStr
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.util.UUID

@Component
class VisitHandler(val visitRepository: VisitRepository,
                   val petRepository: PetRepository,
                   val ownersRepository: OwnersRepository,
                   val ownersHandler: OwnersHandler) {

    fun goToAdd(serverRequest: ServerRequest) =
            petRepository.findById(
                    serverRequest.queryParam("petId").orElseThrow { IllegalArgumentException() })
                .flatMap { pet ->
                    ok().html().render("visits/add", mapOf(
                        "owner" to ownersRepository.findById(pet.owner),
                        "pet" to pet))
                }

    fun add(serverRequest: ServerRequest) =
            serverRequest.body(BodyExtractors.toFormData())
                    .flatMap {
                        val formData = it.toSingleValueMap()
                        visitRepository.save(Visit(
                                id = UUID.randomUUID().toString(),
                                description = formData["description"]!!,
                                petId =  formData["petId"]!!,
                                visitDate = formData["date"]!!.toDate()))
                    }
                    .then(ownersHandler.goToOwnersIndex())


    fun goToEdit(serverRequest: ServerRequest) =
            visitRepository.findById(
                    serverRequest.queryParam("id").orElseThrow({ IllegalArgumentException() }))
                .and { petRepository.findById(it.petId) }
                .map {
                    val (visit, pet) = Pair(it.t1, it.t2)
                    mapOf(
                        Pair("id", visit.id),
                        Pair("date", visit.visitDate.toStr()),
                        Pair("description", visit.description),
                        Pair("pet", pet),
                        Pair("owner", ownersRepository.findById(pet.owner)))
                }
                .flatMap { ok().html().render("visits/edit", it) }


    fun edit(serverRequest: ServerRequest) =
            serverRequest.body(BodyExtractors.toFormData())
                    .flatMap {
                        val formData = it.toSingleValueMap()
                        visitRepository.save(Visit(
                                id = formData["id"]!!,
                                visitDate = formData["date"]!!.toDate(),
                                petId = formData["petId"]!!,
                                description = formData["description"]!!))
                    }
                    .then(ownersHandler.goToOwnersIndex())

}
