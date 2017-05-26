package com.yetanotherdevblog

import com.yetanotherdevblog.petclinic.handlers.OwnersHandler
import com.yetanotherdevblog.petclinic.model.Vet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.server.RouterFunctions.resources
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono


@Configuration
class ApiRoutes(val blogHandler: MyBlogPostHandler) {

    @Bean
    @DependsOn("petClinicRouter")
    fun resourceRouter() = resources("/**", ClassPathResource("static/"))

    @Bean
    fun simpleRouter() = router {
        "/posts".nest {
            GET("/{id}", blogHandler::getBlogPost)
            GET("/", blogHandler::getBlogPosts)
            (accept(APPLICATION_JSON) and (contentType(APPLICATION_JSON) or contentType(APPLICATION_JSON_UTF8))).nest {
                POST("/", blogHandler::save)
            }
        }
        GET("/test", { ok().body(Mono.just("test"), String::class.java) })
        accept(TEXT_EVENT_STREAM).nest { GET("/match", blogHandler::matchClock) }
    }

    @Bean
    fun petClinicRouter(ownersHandler: OwnersHandler) = router {
        "/owners".nest {
            GET("/", ownersHandler::index)
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
            "/delete".nest {
                POST("/", ownersHandler::delete)
            }
        }
    "/vets".nest {
        GET("/", { ok().contentType(TEXT_HTML).render("vets/index", mapOf("vets" to emptyList<Vet>())) })
//        "/add".nest {
//            GET("/", { ok().render("vets/view") })
//            POST("/")
//        }
//        "/edit".nest {
//            GET("/")
//            POST("/")
//        }
    }
    "/specialities".nest {
        GET("/", { ok().contentType(TEXT_HTML).render("specialities/index", mapOf("specialities" to emptyList<Vet>())) })
//        "/add".nest {
//            GET("/")
//            POST("/")
//        }
//        "/edit".nest {
//            GET("/")
//            POST("/")
//        }
//        "/delete".nest {
//            POST("/")
//        }
    }
    "/petTypes".nest {
        GET("/", { ok().contentType(TEXT_HTML).render("petTypes/index", mapOf("petTypes" to emptyList<Vet>())) })
//        "/add".nest {
//            GET("/")
//            POST("/")
//        }
//        "/edit".nest {
//            GET("/")
//            POST("/")
//        }
//        "/delete".nest {
//            POST("/")
//        }
    }

    }

}

