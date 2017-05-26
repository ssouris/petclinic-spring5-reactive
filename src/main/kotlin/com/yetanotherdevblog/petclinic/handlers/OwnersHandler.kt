package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.petclinic.repositories.OwnersRepository
import com.yetanotherdevblog.petclinic.model.Owner
import com.yetanotherdevblog.petclinic.model.Pet
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Mono
import java.util.*

@Component
class OwnersHandler(val ownersRepository: OwnersRepository) {

    fun index(serverRequest: ServerRequest) =
            ok().contentType(MediaType.TEXT_HTML)
                    .render("owners/index", mapOf(
                            Pair("owners", ownersRepository.findAll().map { it.copy(pets = emptySet<Pet>()) } )
                    )
                    )
    fun goToAddPage(serverRequest: ServerRequest) =
            ok().contentType(MediaType.TEXT_HTML).render("owners/add")
    fun goToEditPage(serverRequest: ServerRequest) =
            ok().contentType(MediaType.TEXT_HTML)
                    .render("owners/edit", ownersRepository.findById(getIdFromRequest(serverRequest))
                            .map { mapOf("id" to it.id, "firstName" to it.firstName,
                                    "lastName" to it.lastName, "address" to it.address,
                                    "city" to it.city, "telephone" to it.telephone)
                            })
    fun view(serverRequest: ServerRequest) : Mono<ServerResponse> {
        return ok().contentType(MediaType.TEXT_HTML).render("owners/view", mapOf(
                "owner" to ownersRepository.findById(getIdFromRequest(serverRequest)),
                "pets" to Mono.empty(),
                "petTypes" to Mono.empty(),
                "petVisits" to Mono.empty()
        ))
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
                )).then(ok().render("owners"))
        }
    }

    fun edit(serverRequest: ServerRequest) : Mono<ServerResponse> {
        return ok().body(ownersRepository.findById(getIdFromRequest(serverRequest))
                .flatMap { ownersRepository.save(fromRequest(serverRequest)) }, Owner::class.java)
    }

    fun delete(serverRequest: ServerRequest) : Mono<ServerResponse> {
        return ok().body(ownersRepository.deleteById(getIdFromRequest(serverRequest)), Void::class.java)
    }

//    fun view(@RequestParam("id") idParam: String, model: Model): String {
//        db.withSession {
//
//            val pets = Pets.find { ownerId.equal(owner.id)}.toList()
//
//            val petTypes = hashMapOf<Pet,PetType>()
//            val petVisits = hashMapOf<Pet, List<Visit>>()
//            pets.forEach {
//                petTypes.put(it, PetTypes.find { id.equal(it.typeId) }.single())
//                petVisits.put(it, Visits.find { petId.equal(it.id) }.toList())
//            }
//            model.addAttribute("pets", pets)
//            model.addAttribute("petTypes", petTypes)
//            model.addAttribute("petVisits", petVisits)
//        }
//        return "owners/view"
//    }

//    fun index(
//            @RequestParam("q", required = false) searchParam: String?,
//            model: Model): String {
//        model.addAttribute("searchQuery", if (searchParam != null) searchParam else "")
//        db.withSession {
//            if (searchParam != null && searchParam.isNotEmpty()) {
//                model.addAttribute("owners", Owners.find { text(searchParam) }.map { owner -> Pair(owner, Pets.find { ownerId.equal(owner.id) }.toList())}.toList())
//            } else {
//                model.addAttribute("owners", Owners.find().map { owner -> Pair(owner, Pets.find { ownerId.equal(owner.id) }.toList())}.toList())
//            }
//        }
//        return "owners/index"
//    }

    fun getIdFromRequest(serverRequest: ServerRequest) =
            serverRequest.queryParam("id").orElseThrow { IllegalArgumentException() }

    fun fromRequest(serverRequest: ServerRequest) = Owner(
            id = serverRequest.queryParam("id").orElseThrow { IllegalArgumentException() },
            firstName = serverRequest.queryParam("firstName").orElseThrow { IllegalArgumentException() },
            lastName = serverRequest.queryParam("lastName").orElseThrow { IllegalArgumentException() },
            address = serverRequest.queryParam("address").orElseThrow { IllegalArgumentException() },
            telephone = serverRequest.queryParam("telephone").orElseThrow { IllegalArgumentException() },
            city = serverRequest.queryParam("city").orElseThrow { IllegalArgumentException() }
    )

}