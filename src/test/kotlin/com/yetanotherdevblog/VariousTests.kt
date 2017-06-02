package com.yetanotherdevblog

import com.yetanotherdevblog.petclinic.toLocalDate
import com.yetanotherdevblog.petclinic.toStr
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate

@RunWith(SpringRunner::class)
@SpringBootTest
class VariousTests {

    /**
     * @Value injection need escaping of the `$` char
     * Or you can instead use ConfigurationProperties
     * Or change that special character
     */
    @Value("\${custom.property}")
    lateinit var customProperty : String

    /**
     * Method names can be free text by wrapping them around `...`
     */
    @Test
    fun `@Value properties needs escaping`() {
        Assert.assertEquals(customProperty, "Lorem Ipsum")
    }

    @Test
    fun `Test LocalDate#toStr extension method`() {
        Assert.assertEquals(LocalDate.of(1970, 1, 1).toStr(), "01/01/1970")
    }

    @Test
    fun `Test String#toLocalDate extension method`() {
        Assert.assertEquals("01/01/1970".toLocalDate(), LocalDate.of(1970, 1, 1))
    }

}
