package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.petclinic.repositories.OwnersRepository

import com.yetanotherdevblog.petclinic.model.Owner
import com.yetanotherdevblog.petclinic.model.Pet
import com.yetanotherdevblog.petclinic.model.PetType
import com.yetanotherdevblog.petclinic.model.Visit
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class OwnersHandler(val ownersRepository: OwnersRepository) {

    fun goToOwnersIndex(serverRequest: ServerRequest) = goToOwnersIndex()

    // search query?
    fun goToOwnersIndex(): Mono<ServerResponse> =
            ok().contentType(MediaType.TEXT_HTML).render(
                    "owners/index",
                    mapOf("owners" to ownersRepository.findAll().map { it to emptySet<Pet>() }))

    fun goToAddPage(serverRequest: ServerRequest) =
            ok().contentType(MediaType.TEXT_HTML).render("owners/add")

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
                    }.flatMap { ok().contentType(MediaType.TEXT_HTML).render("owners/edit", it) }

    fun view(serverRequest: ServerRequest) : Mono<ServerResponse> {
        val owner: Mono<Owner> = serverRequest.queryParam("id")
                .map { ownersRepository.findById(it) }.orElse(Mono.empty<Owner>())
        return owner
                .flatMap { owner ->
                    val model = mapOf<String, Any>(
                            "owner" to owner,
                            "pets" to Flux.empty<Pet>(),
                            "petTypes" to Flux.empty<PetType>(),
                            "petVisits" to Flux.empty<Visit>())
                    ok().contentType(MediaType.TEXT_HTML).render("owners/view", model)
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

    fun delete(serverRequest: ServerRequest) : Mono<ServerResponse> {
        val ownerDeleted: Mono<Void> = serverRequest.queryParam("id")
                .map { ownersRepository.deleteById(it) }.orElse(Mono.empty<Void>())
        return ok().body(ownerDeleted, Void::class.java)
    }

}
