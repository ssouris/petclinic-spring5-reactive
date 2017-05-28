package com.yetanotherdevblog.petclinic.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Vet(
        @Id val id:String,
        val firstName: String,
        val lastName : String,
        val specialities: Set<Speciality>)