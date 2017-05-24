package com.yetanotherdevblog

import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body

@org.springframework.stereotype.Component
class MyBlogPostHandler(val myBlogPostRepo: com.yetanotherdevblog.MyBlogPostRepo) {

    fun getBlogPost( serverRequest: org.springframework.web.reactive.function.server.ServerRequest): reactor.core.publisher.Mono<ServerResponse> {
        val id = serverRequest.queryParam("id") ?: return reactor.core.publisher.Mono.empty()
        return ok().json()
                .body(myBlogPostRepo.findById(id.get()), com.yetanotherdevblog.MyBlogPost::class.java)
    }

    fun getBlogPosts( serverRequest: org.springframework.web.reactive.function.server.ServerRequest) =
            ok().json().body(myBlogPostRepo.findAll(), com.yetanotherdevblog.MyBlogPost::class.java)

    fun save(serverRequest: org.springframework.web.reactive.function.server.ServerRequest) =
            ok().json().body(
                    serverRequest.bodyToMono(com.yetanotherdevblog.MyBlogPost::class.java)
                                .map { e -> myBlogPostRepo.save(e) }
                                .doOnSuccess( { println("Save success") }))

    fun matchClock(serverRequest: org.springframework.web.reactive.function.server.ServerRequest) =
            ok().textEventStream()
            .body(reactor.core.publisher.Flux.interval(java.time.Duration.ofMillis(200)).map { "Second $it" }, String::class.java)

    fun experimentingWithWebClient() {
        val client = org.springframework.web.reactive.function.client.WebClient.create("http://localhost:8081")
                .get()
                .uri("/")
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String::class.java)
    }

}