package com.yetanotherdevblog.petclinic

import com.yetanotherdevblog.petclinic.handlers.*
import com.yetanotherdevblog.petclinic.handlers.api.OwnersApiHandler
import com.yetanotherdevblog.petclinic.handlers.api.PetsApiHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunctions.resources
import org.springframework.web.reactive.function.server.router


@Configuration
class PetClinicRoutes() {

    @Bean
    @DependsOn("petClinicRouter")
    fun resourceRouter() = resources("/**", ClassPathResource("static/"))

    @Bean
    fun apiRouter(ownersApiHandler: OwnersApiHandler, petsApiHandler: PetsApiHandler) =
            router {
                (accept(MediaType.APPLICATION_JSON) and "/api").nest {
                    "/owners".nest {
                        GET("/", ownersApiHandler::getOwners)
                        GET("/{id}", ownersApiHandler::getOwner)
                    }
                    "/pets".nest {
                        GET("/", petsApiHandler::getPets)
                        GET("/{id}", petsApiHandler::getPet)
                        GET("/{id}/visits", petsApiHandler::getPetVisits)
                    }
                }
            }

    @Bean
    fun petClinicRouter(welcomeHandler: WelcomeHandler,
                        ownersHandler: OwnersHandler,
                        specialitiesHandler: SpecialitiesHandler,
                        vetsHandler: VetsHandler,
                        petsHandler: PetsHandler,
                        petTypeHandler: PetTypeHandler,
                        visitHandler: VisitHandler) =
            router {
                GET("/", welcomeHandler::welcome)
                "/owners".nest {
                    GET("/", ownersHandler::indexPage)
                    "/add".nest {
                        GET("/", ownersHandler::addPage)
                        POST("/", ownersHandler::add)
                    }
                    "/edit".nest {
                        GET("/", ownersHandler::editPage)
                        POST("/", ownersHandler::edit)
                    }
                    "/view".nest {
                        GET("/", ownersHandler::view)
                    }
                }
                "/vets".nest {
                    GET("/", vetsHandler::indexPage)
                    "/add".nest {
                        GET("/", vetsHandler::addPage)
                        POST("/", vetsHandler::add)
                    }
                    "/edit".nest {
                        GET("/", vetsHandler::editPage)
                        POST("/", vetsHandler::edit)
                    }
                }
                "/pets".nest {
                    "/add".nest {
                        GET("/", petsHandler::addPage)
                        POST("/", petsHandler::add)
                    }
                    "/edit".nest {
                        GET("/", petsHandler::editPage)
                        POST("/", petsHandler::edit)
                    }
                }
                "/visits".nest {
                    "/add".nest {
                        GET("/", visitHandler::addPage)
                        POST("/", visitHandler::add)
                    }
                    "/edit".nest {
                        GET("/", visitHandler::editPage)
                        POST("/", visitHandler::edit)
                    }
                }
                "/specialities".nest {
                    GET("/", specialitiesHandler::indexPage)
                    "/add".nest {
                        GET("/", specialitiesHandler::addPage)
                        POST("/", specialitiesHandler::add)
                    }
                    "/edit".nest {
                        GET("/", specialitiesHandler::editPage)
                        POST("/", specialitiesHandler::edit)
                    }
                }
                "/petTypes".nest {
                    GET("/", petTypeHandler::indexPage)
                    "/add".nest {
                        GET("/", petTypeHandler::addPage)
                        POST("/", petTypeHandler::add)
                    }
                    "/edit".nest {
                        GET("/", petTypeHandler::editPage)
                        POST("/", petTypeHandler::edit)
                    }
                }

            }

}

