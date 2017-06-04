package com.yetanotherdevblog.petclinic.handlers

import com.yetanotherdevblog.petclinic.html
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class WelcomeHandler {

    fun welcome(serverRequest: ServerRequest) = ok().html().render("welcome")

}