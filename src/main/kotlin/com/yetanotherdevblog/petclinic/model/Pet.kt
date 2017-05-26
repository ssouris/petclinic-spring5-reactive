package com.yetanotherdevblog.petclinic.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document
data class Pet(
        @Id val id:String,
        val birthDate: Date,
        val type: PetType,
        @DBRef val owner: Owner,
        val visits : Set<Visit>)