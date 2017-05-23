package com.yetanotherdevblog

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class MyBlogPost(
        @Id val id : String,
        val authorId : String,
        val title : String,
        val description : String,
        val timestamp : LocalDateTime = LocalDateTime.now()
)