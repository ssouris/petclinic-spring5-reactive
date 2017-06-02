package com.yetanotherdevblog.petclinic

import com.yetanotherdevblog.petclinic.handlers.OwnersHandler
import com.yetanotherdevblog.petclinic.handlers.PetTypeHandler
import com.yetanotherdevblog.petclinic.handlers.PetsHandler
import com.yetanotherdevblog.petclinic.handlers.SpecialitiesHandler
import com.yetanotherdevblog.petclinic.handlers.VetsHandler
import com.yetanotherdevblog.petclinic.handlers.VisitHandler
import com.yetanotherdevblog.petclinic.model.Owner
import com.yetanotherdevblog.petclinic.model.Pet
import com.yetanotherdevblog.petclinic.model.Visit
import com.yetanotherdevblog.petclinic.repositories.OwnersRepository
import com.yetanotherdevblog.petclinic.repositories.PetRepository
import com.yetanotherdevblog.petclinic.repositories.VisitRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.web.reactive.function.server.RouterFunctions.resources
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono


@Configuration
class PetClinicRoutes() {

    @Bean
    @DependsOn("petClinicRouter")
    fun resourceRouter() = resources("/**", ClassPathResource("static/"))

    @Bean
    fun apiRouter(ownersRepository: OwnersRepository,
                  petRepository: PetRepository,
                  visitRepository: VisitRepository) = router {
        (accept(MediaType.APPLICATION_JSON) and "/api").nest {
            "/owners".nest {
                GET("/", { ok().body(ownersRepository.findAll(), Owner::class.java) })
                GET("/{id}", { ok().body(ownersRepository.findById(it.pathVariable("id")), Owner::class.java) })
            }
            "/pets".nest {
                GET("/", { ok().body(petRepository.findAll(), Pet::class.java) })
                GET("/{id}", { ok().body(petRepository.findById(it.pathVariable("id")), Pet::class.java) })
                GET("/{id}/visits", { ok().body(visitRepository.findByPetId(it.pathVariable("id")), Visit::class.java) })
            }
        }
    }

    @Bean
    fun petClinicRouter(ownersHandler: OwnersHandler,
                        specialitiesHandler: SpecialitiesHandler,
                        vetsHandler: VetsHandler,
                        petsHandler: PetsHandler,
                        petTypeHandler: PetTypeHandler,
                        visitHandler: VisitHandler) =
            router {
                GET("/", { ok().html().render("welcome") })
                "/owners".nest {
                    GET("/", ownersHandler::goToOwnersIndex)
                    "/add".nest {
                        GET("/", ownersHandler::goToAddPage)
                        POST("/", ownersHandler::add)
                    }
                    "/edit".nest {
                        GET("/", ownersHandler::goToEditPage)
                        POST("/", ownersHandler::edit)
                    }
                    "/view".nest {
                        GET("/", ownersHandler::view)
                    }
                }
                "/vets".nest {
                    GET("/", vetsHandler::goToVetsIndex)
                    "/add".nest {
                        GET("/", vetsHandler::goToAdd)
                        POST("/", vetsHandler::add)
                    }
                    "/edit".nest {
                        GET("/", vetsHandler::goToEdit)
                        POST("/", vetsHandler::edit)
                    }
                }
                "/pets".nest {
                    "/add".nest {
                        GET("/", petsHandler::goToAdd)
                        POST("/", petsHandler::add)
                    }
                    "/edit".nest {
                        GET("/", petsHandler::goToEdit)
                        POST("/", petsHandler::edit)
                    }
                }
                "/visits".nest {
                    "/add".nest {
                        GET("/", visitHandler::goToAdd)
                        POST("/", visitHandler::add)
                    }
                    "/edit".nest {
                        GET("/", visitHandler::goToEdit)
                        POST("/", visitHandler::edit)
                    }
                }
                "/specialities".nest {
                    GET("/", specialitiesHandler::goToSpecialitiesIndex)
                    "/add".nest {
                        GET("/", specialitiesHandler::goToAdd)
                        POST("/", specialitiesHandler::add)
                    }
                    "/edit".nest {
                        GET("/", specialitiesHandler::goToEdit)
                        POST("/", specialitiesHandler::edit)
                    }
                }
                "/petTypes".nest {
                    GET("/", petTypeHandler::goToPetTypeIndex)
                    "/add".nest {
                        GET("/", petTypeHandler::goToAdd)
                        POST("/", petTypeHandler::add)
                    }
                    "/edit".nest {
                        GET("/", petTypeHandler::goToEdit)
                        POST("/", petTypeHandler::edit)
                    }
                }

            }

}

