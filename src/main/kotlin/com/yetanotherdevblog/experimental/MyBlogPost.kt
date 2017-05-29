package com.yetanotherdevblog.experimental

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@org.springframework.data.mongodb.core.mapping.Document
data class MyBlogPost(
        @org.springframework.data.annotation.Id val id : String,
        val authorId : String,
        val title : String,
        val description : String,
        val timestamp : java.time.LocalDateTime = java.time.LocalDateTime.now()
)
