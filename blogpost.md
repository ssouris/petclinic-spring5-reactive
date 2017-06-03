I wanted to get my hands dirty with Kotlin and Spring 5 so I decided to develop a small application called PetClinic.

### PetClinic

The application is a bookkeeping application for a pet clinic. The idea is you have owners who bring their pets for a visit to the pet clinic. The original idea came from

*   https://github.com/spring-projects/spring-petclinic
*   https://github.com/spring-petclinic/spring-framework-petclinic

### Why Kotlin?

I love Java but Kotlin has a bunch of nice features that Java just doesn't at the moment. You can check some of the features [here](https://kotlinlang.org/docs/reference/). You can also check my slides from a brown bag session I recently did.

<iframe src="//www.slideshare.net/slideshow/embed_code/key/FgUXL988xQQbRO" width="595" height="485" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;" allowfullscreen=""></iframe>

### kotlin-spring plugin + no-arg plugin

Kotlin classes and methods are final by default. Frameworks like Spring that use CGLIB need classes to be open, because they create proxies that dynamically extend classes for you.

So, instead of explicitly adding the `open` keyword all over the place you can use the **kotlin-spring** plugin that makes classes annotated with specific annotation open (e.x. classes annotated with @Configuration or @Service). It seems that Spring already has integrated that so you don't have to.

The **no-arg** plugin generates a zero-argument constructor for classes with a specific annotation. That constructor is not directly accessible but you can use reflection to access it. This allows JPA or (in this case) Spring Data MongoDB to instantiate the data class.

### The models

Based on the idea that we frequently create a class that does nothing but hold data, data classes in Kotlin are special classes that the compile automatically derives getters/setters, equals, hashcode, toString, copy by adding the keyword `data`. Similar to what you can do with `@Data` annotation from [lombok](https://projectlombok.org/) project. Data classes also support destructuring (see below).

### The repositories

I am using MongoDB with the reactive driver and this is how you can declare repositories using Spring Data MongoDB Reactive (ReactiveMongoRepository brings methods like insert, findAll etc) More info [here](http://docs.spring.io/spring-data/data-mongo/docs/2.0.0.M3/reference/html/#mongo.reactive.repositories)

### Functional Web API

Spring 5 introduces the Functional Web API where you can declare your routes without using a DSL. Currently, you cannot mix functional API and annotation model (e.x. @RestController, @RequestMapping etc) in the same codebase.

The two main classes in this API are `HandlerFunction`

that is the alternative of Servlet.service(..., ...) but is side effect free.

Then the `RouterFunction` is the alternative of `@RequestMapping` annotation and routes the incoming requests to the appropriate handlers using RequestPredicate(s) you declare.

ServerRequest and ServerResponse are immutable interfaces that offer access to the underlying HTTP messages. Typically, you do not write router functions yourself, but rather use `RouterFunctions.route(RequestPredicate, HandlerFunction)` to create one using a request predicate and handler function. If the predicate(s) applies, the request is routed to the given handler function, otherwise no routing is performed, resulting in a 404 Not Found response.

This is how the Kotlin DSL looks like:

### Kotlin lateinit in @Autowired fields

It's worth noticing here the lateinit modifier. Kotlin assumes that a member is not null and that's why it must be assigned explicitly in the constructor. Since Spring Framework takes care of the autowiring after the constructor has run have to use lateinit

### Extension methods

Extension methods are really powerful in Kotlin and one of my favorite features. One use case for them in Java codebases can be in to replace all those Util classes (Util classes hell) with extension methods that seem more natural and readable. Here are a couple of extensions I wrote for this app.

### Extract Body using Flux, Mono

Spring 5 gives a variety of `BodyExtractors`, so that you can get the body as a Mono, a Flux or even form data as multivalue map (Here I am transforming the multivalue map to sigle value map)

### Chaining Mono/Flux

In the following snippet I am trying to combine multiple Flux(es) to create a response that Thymeleaf then renders in the server and sends to the browser. I get the id query param, then query for the owner in the database, then after I know the owner I query for the pets. Then in the final step, I am creating the map that Thymeleaf uses to render the HTML.

### Kotlin Type Inference

I also used a lot of automatic type inference in this project. For example:

It can lead to less typing, increased readability and the famous one-liners.

### Attributions

In this app the frontend (Thymeleaf) is not mine. I adapted it from [https://github.com/cheptsov/kotlin-nosql-mongodb-petclinic/](https://github.com/cheptsov/kotlin-nosql-mongodb-petclinic/).

### Summary

This post was packed with a lot things. And I was just enumerating things I used while developing this application. Spring 5 new features are really neat and Kotlin is something to keep an eye on and start using more frequently, it's interoperability with Java is just out of this world, making it possible to use it in Java codebases interchangeably. I really need at some point to stress test this application and compare it to the blocking equivalent, but that's a subject for another post. Thanks for reading and as always the code is [here](https://github.com/ssouris/petclinic-spring5-reactive)

### Links

*   Mixit Reactive Spring 5 + Kotlin application: https://github.com/mixitconf/mixit
*   Kotlin Spring 5 application: https://github.com/cheptsov/kotlin-nosql-mongodb-petclinic
*   Kotlin reference: https://kotlinlang.org/docs/reference/