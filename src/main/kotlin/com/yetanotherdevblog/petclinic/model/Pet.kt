package com.yetanotherdevblog.petclinic.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.Date
import java.util.UUID

@Document
data class Pet(
        @Id val id:String = UUID.randomUUID().toString(),
        val name: String,
        val birthDate: LocalDate,
        val type: String,
        val owner: String)
