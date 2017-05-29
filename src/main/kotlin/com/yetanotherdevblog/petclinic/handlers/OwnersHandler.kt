package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.petclinic.html
import com.yetanotherdevblog.petclinic.repositories.OwnersRepository

import com.yetanotherdevblog.petclinic.model.Owner
import com.yetanotherdevblog.petclinic.model.Pet
import com.yetanotherdevblog.petclinic.repositories.PetRepository
import com.yetanotherdevblog.petclinic.repositories.PetTypeRepository
import com.yetanotherdevblog.petclinic.repositories.VisitRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class OwnersHandler(val ownersRepository: OwnersRepository,
                    val petRepository: PetRepository,
                    val petTypeRepository: PetTypeRepository,
                    val visitRepository: VisitRepository) {

    fun goToOwnersIndex(serverRequest: ServerRequest) = goToOwnersIndex()

    fun goToOwnersIndex(): Mono<ServerResponse> {
        return ok().html().render("owners/index",
                mapOf("owners" to ownersRepository.findAll().map { Pair(it, emptySet<Pet>()) },
                        "pets" to petRepository.findAll().collectMultimap { it.owner }))
    }

    fun goToAddPage(serverRequest: ServerRequest) =
            ok().html().render("owners/add")

    fun goToEditPage(serverRequest: ServerRequest) =
            serverRequest.queryParam("id")
                    .map { ownersRepository.findById(it) }
                    .orElse(Mono.empty<Owner>())
                    .map { mapOf("id" to it.id,
                                "firstName" to it.firstName,
                                "lastName" to it.lastName,
                                "address" to it.address,
                                "city" to it.city,
                                "telephone" to it.telephone)
                    }.flatMap { ok().html().render("owners/edit", it) }

    fun view(serverRequest: ServerRequest) : Mono<ServerResponse> {
        return serverRequest.queryParam("id")
                .map { ownersRepository.findById(it) }
                .orElse(Mono.empty<Owner>())
                .flatMap { owner ->
                    val model = mapOf<String, Any>(
                            "owner" to owner,
                            "pets" to petRepository.findAllByOwner(owner.id),
                            "petTypes" to petTypeRepository.findAll().collectMap({ it.id }, {it.name}),
                            "petVisits" to visitRepository.findAll().collectMultimap { it.petId })
                    ok().html().render("owners/view", model)
                }
                .switchIfEmpty(ServerResponse.notFound().build())
    }

    fun add(serverRequest: ServerRequest) : Mono<ServerResponse> {
        return serverRequest.body(BodyExtractors.toFormData()).flatMap {
                val formData = it.toSingleValueMap()
                ownersRepository.save(Owner(
                               id = formData["id"] ?: UUID.randomUUID().toString(),
                        firstName = formData["firstName"]!!,
                         lastName = formData["lastName"]!!,
                          address = formData["address"]!!,
                        telephone = formData["telephone"]!!,
                             city = formData["city"]!!
                ))
        }.then(goToOwnersIndex())
    }

    fun edit(serverRequest: ServerRequest) : Mono<ServerResponse> {
        val owner: Mono<Owner> = serverRequest.queryParam("id")
                .map { ownersRepository.findById(it) }.orElse(Mono.empty<Owner>())
        return owner
                .flatMap { ownersRepository.save(it) }
                .flatMap { ok().render("owners/edit", it) }
    }

}


