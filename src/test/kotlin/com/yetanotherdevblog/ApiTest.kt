package com.yetanotherdevblog

import com.yetanotherdevblog.petclinic.model.Owner
import com.yetanotherdevblog.petclinic.repositories.PetRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.test
import reactor.core.publisher.toMono
import java.time.Duration

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiTest {

    @LocalServerPort
    var port: Int? = null

    lateinit var client: WebClient

    @Autowired
    lateinit var petRepository: PetRepository

    @Before
    fun setup() {
        client = WebClient.create("http://localhost:$port")
    }

    @Test
    fun `API call for Owners`() {
        client.get().uri("/api/owners").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Owner::class.java)
                .test()
                .consumeNextWith {
                    assertEquals("James", it.firstName)
                }
                .verifyComplete()

        client.get().uri("/api/owners/5bead0d3-cd7b-41e5-b064-09f48e5e6a08").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Owner::class.java)
                .test()
                .consumeNextWith {
                    assertEquals("James", it.firstName)
                    assertEquals("Owner", it.lastName)
                }
                .verifyComplete()
    }
}