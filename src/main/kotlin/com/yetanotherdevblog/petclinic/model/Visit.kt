package com.yetanotherdevblog.petclinic.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Visit(
        @Id val id:String,
        val visitDate: java.time.LocalDate,
        val description: String,
        val petId: String)