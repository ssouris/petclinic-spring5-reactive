package com.yetanotherdevblog

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.test

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OwnersTest {

    @LocalServerPort
    var port: Int? = null

    lateinit var client: WebClient

    @Before
    fun setup() {
        client = WebClient.create("http://localhost:$port")
    }

    @Test
    fun home() {
        client.get().uri("/owners").accept(MediaType.TEXT_HTML)
                .exchange()
                .test()
                .expectNextMatches { it.statusCode() == HttpStatus.OK }
                .verifyComplete()
    }
}