# PetClinic (Spring 5/Reactive)
[![Build Status](https://travis-ci.org/ssouris/petclinic-spring5-reactive.svg)](https://travis-ci.org/ssouris/petclinic-spring5-reactive)

This is a rewrite of PetClinic using Kotlin, Spring 5 and its reactive APIs (Flux, Mono).

## Software design
 - Reactive and non-blocking
 - Leverage Kotlin features like [Kotlin extensions] and [reified type parameters] for cleaner code

### Technologies used

 - Language: [Kotlin](https://kotlin.link/) 
 - Web framework: [Spring Boot](https://projects.spring.io/spring-boot/) and [Spring Web Reactive Functional](https://spring.io/blog/2016/09/22/new-in-spring-5-functional-web-framework)
 - Engine: [Netty](http://netty.io/) used for client and server
 - Reactive API: [Reactor](http://projectreactor.io/)
 - Persistence : [Spring Data Reactive MongoDB](https://spring.io/blog/2016/11/28/going-reactive-with-spring-data)
 - Build: [Gradle Script Kotlin](https://github.com/gradle/gradle-script-kotlin)
 - Testing: [Junit](http://junit.org/) 
 
### Run the app in dev mod using command line
 - Run `./gradlew bootRun` in another terminal
 - Open `http://localhost:8080/` in your browser
 - If you want to debug the app, add `--debug-jvm` parameter to Gradle command line
  
### Package and run the application from the executable JAR:
```
./gradlew clean build
java -jar build/libs/......-1.0.0-SNAPSHOT.jar
```

## TODO

 - Validation on save/edit
 - Error handling (what happens when an entity is not present in the db)

