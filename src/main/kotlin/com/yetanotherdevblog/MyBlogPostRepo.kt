package com.yetanotherdevblog

import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository


@Repository
class MyBlogPostRepo(val template: ReactiveMongoTemplate) {

    fun findById(id: String) = template.findById(id, MyBlogPost::class.java)

    fun findAll() = template.findAll<MyBlogPost>()

}
