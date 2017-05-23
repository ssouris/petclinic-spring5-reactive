package com.yetanotherdevblog

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux
import java.time.Duration

@Component
class MyBlogPostHandler(val myBlogPostRepo: MyBlogPostRepo) {

    fun getBlogPosts( serverRequest: ServerRequest) = ok().json().body(myBlogPostRepo.findAll())

    fun matchClock(serverRequest: ServerRequest) = ok()
            .textEventStream()
            .body(Flux.interval(Duration.ofMillis(200)).map { "Second ${it}" }, String::class.java)

}