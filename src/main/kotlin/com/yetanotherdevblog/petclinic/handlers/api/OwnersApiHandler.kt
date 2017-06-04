package com.yetanotherdevblog.petclinic.handlers.api

import com.yetanotherdevblog.petclinic.model.Owner
import com.yetanotherdevblog.petclinic.repositories.OwnersRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class OwnersApiHandler(val ownersRepository: OwnersRepository) {

    fun getOwners(serverRequest: ServerRequest) =
        ok().body(ownersRepository.findAll(), Owner::class.java)

    fun getOwner(serverRequest: ServerRequest) =
            ok().body(ownersRepository.findById(serverRequest.pathVariable("id")), Owner::class.java)

}