package com.yetanotherdevblog

import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class ReactiveApplication

fun main(args: Array<String>) {
    run(ReactiveApplication::class, *args)
}
