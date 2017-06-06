package com.yetanotherdevblog

import com.yetanotherdevblog.petclinic.handlers.OwnersHandler
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.test

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OwnersHandlerTest {


    @Autowired
    lateinit var ownersHandler: OwnersHandler

    @Test
    fun `findByNameLike returns one result firstName`() {
        ownersHandler.findByNameLike("owner")
                .test()
                .expectNextMatches({ it.firstName == "James" })
                .verifyComplete()
    }

    @Test
    fun `findByNameLike returns one result lastName`() {
        ownersHandler.findByNameLike("owner")
                .test()
                .expectNextMatches({ it.firstName == "James" })
                .verifyComplete()
    }

    @Test
    fun `findByNameLike returns no result`() {
        ownersHandler.findByNameLike("No Result")
                .test()
                .expectNextCount(0)
                .verifyComplete()
    }

}