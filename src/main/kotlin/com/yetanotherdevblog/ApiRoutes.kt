package com.yetanotherdevblog

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono


@Configuration
class ApiRoutes(val blogHandler: MyBlogPostHandler) {

    @Bean
    fun simpleRouter() = router {
        "/posts".nest {
            GET("/{id}", blogHandler::getBlogPost)
            GET("/",     blogHandler::getBlogPosts)
            (accept(APPLICATION_JSON) and (contentType(APPLICATION_JSON) or contentType(APPLICATION_JSON_UTF8))).nest {
                POST("/",    blogHandler::save)
            }
        }
        GET("/test", { ServerResponse.ok().body(Mono.just("test"), String::class.java) })
        accept(TEXT_EVENT_STREAM).nest { GET("/match", blogHandler::matchClock) }
    }

}
