package com.yetanotherdevblog.petclinic.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document
data class Vet(
        @Id val id:String = UUID.randomUUID().toString(),
        val firstName: String,
        val lastName : String,
        val specialities: Set<String> = emptySet())
