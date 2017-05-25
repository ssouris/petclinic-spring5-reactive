package com.yetanotherdevblog.petclinic

import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import java.time.LocalDate
import java.util.*


data class Owner(val address: String,
                 val city: String,
                 val telephone: String,
                 val pets: Set<Pet>)

data class Pet(
        val birthDate: Date,
        val type: PetType,
        val owner: Owner,
        val visits : Set<Visit>)

data class PetType(val name: String);

data class Speciality(val name: String)

data class Vet(
        val firstName: String,
        val lastName : String,
        val specialities: Set<String>)

data class Visit(
        val visitDate: LocalDate,
        val description: String,
        val petId: String)

// api skeleton
fun petClinicRouter() = router {
    "/owners".nest {
        GET("/")
        "/add".nest {
            GET("/")
            POST("/")
        }
        "/edit".nest {
            GET("/")
            POST("/")
        }
        "/view".nest {
            GET("/")
        }
        "/delete".nest {
            POST("/")
        }
    }
    "/vets".nest {
        GET("/")
        "/add".nest {
            GET("/")
            POST("/")
        }
        "/edit".nest {
            GET("/")
            POST("/")
        }
    }
    "/specialities".nest {
        GET("/")
        "/add".nest {
            GET("/")
            POST("/")
        }
        "/edit".nest {
            GET("/")
            POST("/")
        }
        "/delete".nest {
            POST("/")
        }
    }
    "/petTypes".nest {
        GET("/")
        "/add".nest {
            GET("/")
            POST("/")
        }
        "/edit".nest {
            GET("/")
            POST("/")
        }
        "/delete".nest {
            POST("/")
        }
    }

}
