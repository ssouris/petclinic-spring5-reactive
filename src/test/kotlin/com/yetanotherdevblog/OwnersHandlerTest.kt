package com.yetanotherdevblog

import com.yetanotherdevblog.petclinic.handlers.OwnersHandler
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.test
import reactor.core.publisher.toMono

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OwnersHandlerTest {

    @Autowired
    lateinit var ownersHandler: OwnersHandler

    @Test
    fun `findByNameLike returns no result`() {
        ownersHandler.findByNameLike("No Result")
                .test()
                .expectNextCount(0)
                .verifyComplete()
    }

}