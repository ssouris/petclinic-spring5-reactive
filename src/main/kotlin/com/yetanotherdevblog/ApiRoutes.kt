package mixit.web

import com.yetanotherdevblog.MyBlogPostHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.server.router


@Configuration
class ApiRoutes(val myBlogPostHandler: MyBlogPostHandler) {

    @Bean
    fun simpleRouter() = router {
        (accept(APPLICATION_JSON) and "/custom-blog").nest {
            "/getposts".nest {
                GET("/", myBlogPostHandler::getBlogPosts)
            }
        }
        accept(TEXT_EVENT_STREAM).nest {
            GET("/match", myBlogPostHandler::matchClock)
        }
    }

}
