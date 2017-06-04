# A practical intro to Kotlin

![LOGO](https://kotlinlang.org/assets/images/open-graph/kotlin_250x250.png)

by Stathis Souris

## The Java Platform is...

- ...a mature and extensive hardware independent platform.
- ...evolving very, very slowly
- ...a platform for many programming languages:
- Java, Scala, Kotlin, Ceylon, Frege, Groovy, Fantom, Gosu, ...
- Groovy, Clojure, JRuby, Jython, Golo

## Why we need change?

- Change threatens backward compatibility
- Change enabled new knowledge, new techniques, new tools
- How do we make software engineering easier and nicer for ourselves
- Where would we be if we didn't embrace change 
   (Machine language?, Assembly?)

## What is Kotlin?

Kotlin is a statically-typed programming language that runs on the Java Virtual Machine 
and also can be compiled to JavaScript source code or uses the LLVM compiler infrastructure. 
Its primary development is from a team of JetBrains programmers based in Saint Petersburg, 
Russia (the name comes from Kotlin Island, near St. Petersburg).

### Influenced by Effective Java

![LOGO](http://ecx.images-amazon.com/images/I/51poMjBx7PL.jpg)

## Why Kotlin?
 - Open source under Apache 2.0
 - Expressive
 - Null Safety & Immutability
 - Type Inference, Smart Casts
 - Java Interoperability
 - String templates
 - Open programming styles
 - Lamdas feel at home (nuff said)
 - Data class
 - Default values for function parameters
 - Extension Functions
 - when expression (switch on steroids)
 
### Kotlin runtime
<table>
  <tr>
    <th>Library</th>
    <th>Jar Size</th> 
    <th>Dex Size</th>
    <th>Method count</th>
    <th>Field count</th>
  </tr>
  <tr>
    <td>kotlin-runtime-0.10.195</td>
    <td>354 KB</td>
    <td>282 KB</td>
    <td>1071</td>
    <td>391</td>
  </tr>
  <tr>
    <td>kotlin-stdlib-0.10.195</td>
    <td>541 KB</td>
    <td>835 KB</td>
    <td>5508</td>
    <td>458</td>
  </tr>
</table>

## Compared to Groovy and Scala runtimes

<table>
  <tr>
    <th>Library</th>
    <th>Jar Size</th> 
    <th>Dex Size</th>
    <th>Method count</th>
    <th>Field count</th>
  </tr>
  <tr>
    <td>scala-library-2.11.5</td>
    <td>5.3 MB</td>
    <td>4.9 MB</td>
    <td>50801</td>
    <td>5820</td>
  </tr>
  <tr>
    <td>groovy-2.4.0-grooid</td>
    <td>4.5 MB</td>
    <td>4.5 MB</td>
    <td>29636</td>
    <td>8069</td>
  </tr>
</table> 

## val/var

In Kotlin favours immutability over mutability. 

```kotlin
// val = immutable object
val aString : String = "Lorem ipsum" // no semicolons
aString = "Lorem Ipsum Lorem ipsum" // compiler error

// var = mutable obect
var mutableString :String = "Lorem ipsum"// underlined by IDE
```

## type inference

Types can be infered (when there is enough information about them)

```kotlin
val anInt = 42 // compiler infers type to Int
val someString = "Lorem Ipsum"
```

## functions

```kotlin
fun add(left: Int, right: Int): Int {
    return left + right
}
```

## named & default args

```kotlin
fun sayHi(hello: String = "Hello",
          world: String = "World") {
    println("$hello, $world!")
}

sayHi()                 // Hello World!
sayHi(hello = "Stathis") // Hello Stathis!
sayHi(hello = "Stathis",    // Stathis lorem ipsum!
      world = "lorem ipsum")
```

## If statement as an expression (Java style) 1/3

```kotlin
fun getResult(condition: Boolean): String {
    val result = null
    if (condition) {
        result = "Service OK"
    } else {
        result = "Service Failure"
    }
    return result
}
```

### If statement as an expression (better) 2/3

```kotlin
fun getResult(condition: Boolean): String {
    return if (condition) {
        "Service OK"
    } else {
        "Service Failure"
    }
}
```

### If statement as an expression (Kotlin way) 3/3

```kotlin
fun getResult(condition: Boolean) = 
  if (condition) "Service OK" else "Service Failure"
```

## String interpolation

String interpolation and string templates

```kotlin
val customer = Customer("Stathis", "Souris")

val templatedString = """
|${customer.firstName}
|${customer.lastName}
"""

fun main (args: List<String>) {
  println("Name is ${customer.firstName}")
  // or if i want the toString I just omit the properties
  println("Name is $customer")
}
```

## Extension methods

```kotlin
fun Date.isTuesday() : Boolean {
  return day == 2
}

val epoch: Date = Date(1970, 0, 0)
if (epoch.isTuesday()) {
  println("The epoch was a Tuesday.")
} else {
  println("The epoch was not a Tuesday.")
}
```

## Destructuring

Destructuring types is a nice concept that can lead to readable code.
The nicest thing it that you can use it in lamdas too where you do not want
to be chatty

```kotlin
// destructuring parameters
val (name, email, id) = Tuple("Stathis", "foo@bar.com", 1);
val (firstName, lastName) = Customer("lorem", "ipsum")
listOf(Customer("lorem", "ipsum")).map { (firstName, lastName) -> ..... }
val countryAndCity = mapOf(
                Pair("Madrid", "Spain"), 
                "Paris" to "France"))
for ((city, country) in countryAndCity) { ... }
```

## Ranges

```kotlin
// ranges
for (numer in 1..100) { .. }
1..100.filter { e -> e % 2 == 0 }.map { ... }
```

## Smart cast

The concept of smart cast makes code readable in cases where you check
if a type is instanceOf a type and you end up casting the type a few lines later.
In Kotlin this will not happen.

```kotlin
// classes by default final, add `open` to inherit from
open class Person { }
class Employee(val vacationDays: Int): Person() {}
class Contractor: Person()

fun validateVacations(person: Person) {
  if (person is Employee) {
    // casting is inferred by the compiler (smart cast)
    if (person.vacationDays != 20) {
      println("You need to take some more time off!")
    }
  }
}
```

## Safe Calls

In Kotlin by default everything is non-nullable. If you want 
a type to be nullable you must explicitly add `?`.
So when you want to access a property from a nullable type you must use
`?.` or else the compiler will not let you.

```kotlin
val customer: Customer = ...
println(customer.firstName)

val customer: Customer? = ...
println(customer?.firstName) // if customer null then prints null

// elvis operator
val l: Int = if (b != null) b.length else -1
val l = b?.length ?: -1

```

## Micro DSL 

You can build DSLs. Here is an example from Spring 5
Functional Web API

```kotlin
// Spring 5 FunctionRouter registration in kotlin
"/posts".nest {
  GET("/{id}", blogHandler::getBlogPost)
  GET("/",     blogHandler::getBlogPosts)
}
accept(TEXT_EVENT_STREAM).nest { 
  GET("/match", blogHandler::matchClock) 
}
```

## Type Alias

You can rename a complex type

```kotlin
typealias MapOfStringsToObjects = HashMap<String, Object>
```

## When

```kotlin
val validNumbers = arrayOf(100, 42, 5566, 234)

when(x) {
  in 1..10 -> print("x == 1")
  in validNumbers -> print("x is valid range")
  !in 10..20 -> print("x is ouside range")
  is ClassName -> ... (smart cast also works here)
  else -> print("None of the above")
}
```

## Java method overloading

```java
public String foo(String name, int number, boolean toUpperCase) {
    return (toUpperCase ? name.toUpperCase() : name) + number;
}
public String foo(String name, int number) {
    return foo(name, number, false);
}
public String foo(String name, boolean toUpperCase) {
    return foo(name, 42, toUpperCase);
}
public String foo(String name) {
    return foo(name, 42);
}
```

## Can be replaced with one function

```kotlin
fun foo(name: String,number: Int = 42, toUpperCase: Boolean = false) =
   (if (toUpperCase) name.toUpperCase() else name) + number
             
fun useFoo() = listOf(
        foo("a"),
        foo("b", number = 1),
        foo("c", toUpperCase = true),
        foo(name = "d", number = 2, toUpperCase = true))
```

## Null checks in Java

```java
public void sendMessageToClient(
    @Nullable Client client,
    @Nullable String message) {
    if (client == null || message == null) return;

    PersonalInfo personalInfo = client.getPersonalInfo();
    if (personalInfo == null) return;

    String email = personalInfo.getEmail();
    if (email == null) return;

    sendMessage(email, message);
}
```

## Null safety in Kotlin

```kotlin
fun sendMessageToClient(
        client: Client?, message: String?) {
    sendMessage(
      client?.personalInfo?.email ?: return, 
      message ?: return );
}

class Client (val personalInfo: PersonalInfo?)

class PersonalInfo (val email: String?)

```

## Delegation in Kotlin

```kotlin
class CopyPrinter(copier: Copy, printer: Print) : Copy by copier, Print by printer
interface Copy {
    fun copy(page: Page): Page
}
interface Print {
    fun print(page: Page)
}
```

### Java decompiled code

```java
public final class CopyPrinter implements Copy, Print {
   private final Copy $$delegate_0;// $FF: synthetic field
   private final Print $$delegate_1;// $FF: synthetic field

   public CopyPrinter(@NotNull Copy copier, @NotNull Print printer) {
      Intrinsics.checkParameterIsNotNull(copier, "copier");
      Intrinsics.checkParameterIsNotNull(printer, "printer");
      super();
      this.$$delegate_0 = copier;
      this.$$delegate_1 = printer;
   }

   @NotNull
   public Page copy(@NotNull Page page) {
      Intrinsics.checkParameterIsNotNull(page, "page");
      return this.$$delegate_0.copy(page);
   }

   public void print(@NotNull Page page) {
      Intrinsics.checkParameterIsNotNull(page, "page");
      this.$$delegate_1.print(page);
   }
}
``` 

## Sealed classes

Defining restricted class hierarchies

```kotlin
sealed class Expr {
  class Num(val value: Int): Expr()
  class Sum(val left: Expr, val right: Expr): Expr()
}
fun eval(e: Expr): Int =
  when (e) {
    is Expr.Num -> e.value
    is Expr.Sum -> eval(e.right) + eval(e.left)
  }
```

## Links

- Devoxx UK 2017 - Russel Winder 
  https://www.youtube.com/watch?v=cFL_DDXBkJQ
  https://github.com/russel/Factorial
- Jake Wharton pitch
  https://docs.google.com/document/d/1ReS3ep-hjxWA8kZi0YqDbEhCqTt29hG8P44aA9W0DM8/edit
- Spring Framework support
  https://spring.io/blog/2017/01/04/introducing-kotlin-support-in-spring-framework-5-0
