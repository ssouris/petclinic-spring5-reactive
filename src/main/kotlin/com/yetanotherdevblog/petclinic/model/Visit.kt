package com.yetanotherdevblog.petclinic.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document
data class Visit(
        @Id val id:String = UUID.randomUUID().toString(),
        val visitDate: LocalDate,
        val description: String,
        val petId: String)