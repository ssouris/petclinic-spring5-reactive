package com.yetanotherdevblog

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class ReactiveApplication

fun main(args: Array<String>) {
    SpringApplication.run(ReactiveApplication::class.java, *args)
}
