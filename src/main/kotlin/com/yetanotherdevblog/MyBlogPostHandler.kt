package com.yetanotherdevblog

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class MyBlogPostHandler(val myBlogPostRepo: MyBlogPostRepo) {

    fun getBlogPost( serverRequest: ServerRequest): Mono<ServerResponse> {
        val id = serverRequest.queryParam("id") ?: return Mono.empty()
        return ok().json()
                .body(myBlogPostRepo.findById(id.get()), MyBlogPost::class.java)
    }

    fun getBlogPosts( serverRequest: ServerRequest) =
            ok().json().body(myBlogPostRepo.findAll(), MyBlogPost::class.java)

    fun save(serverRequest: ServerRequest) =
            ok().json().body(
                    serverRequest.bodyToMono(MyBlogPost::class.java)
                                .map { e -> myBlogPostRepo.save(e) }
                                .doOnSuccess( { println("Save success") }))

    fun matchClock(serverRequest: ServerRequest) =
            ok().textEventStream()
            .body(Flux.interval(Duration.ofMillis(200)).map { "Second $it" }, String::class.java)

    fun experimentingWithWebClient() {
        val client = WebClient.create("http://localhost:8081")
                .get()
                .uri("/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String::class.java)
    }

}