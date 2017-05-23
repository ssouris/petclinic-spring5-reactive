package com.yetanotherdevblog

import org.springframework.boot.SpringApplication
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.Flux
import kotlin.reflect.KClass

fun run(type: KClass<*>, vararg args: String) = SpringApplication.run(type.java, *args)

inline fun <reified T : Any> ReactiveMongoOperations.findAll(): Flux<T> =
        findAll(T::class.java)

fun BodyBuilder.json() = contentType(APPLICATION_JSON_UTF8)

fun BodyBuilder.textEventStream() = contentType(TEXT_EVENT_STREAM)
