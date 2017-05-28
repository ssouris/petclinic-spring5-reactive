package com.yetanotherdevblog

import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.http.ResponseEntity
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux

inline fun <reified T : Any> ReactiveMongoOperations.findAll(): Flux<T> =
        findAll(T::class.java)

fun ServerResponse.BodyBuilder.json() = contentType(APPLICATION_JSON_UTF8)

fun ServerResponse.BodyBuilder.textEventStream() = contentType(TEXT_EVENT_STREAM)

fun ServerResponse.BodyBuilder.html() = contentType(TEXT_HTML)

// port StringUtils to Kotlin method extensions
fun CharSequence.isEmpty() = this.length == 0
fun CharSequence.isNotEmpty() = !this.isEmpty()



// BAD THINGS - DO NOT DO THIS

// method names can be free text
infix fun CharSequence.`are you ok man`(arg1 : String)
        = println("Yeap")

fun main1(args: Array<String>) {
    // with infix methods can skip the '(', ')'
    "Stathis Souris " `are you ok man` "?"
    println("st" + "asd")
}

fun String.plus(other: Any?)= when (other) {
        is String -> this.toUpperCase().plus(other.toUpperCase())
        else -> throw IllegalArgumentException()
}

//fun main(args: Array<String>) {
    //    "Stathis Souris " `are you ok man` "?"
//    println("st" + "asd")
//}
