package com.yetanotherdevblog.factorial

val zero = java.math.BigInteger.ZERO
val one = java.math.BigInteger.ONE
val two = 2.bigint

fun validate(x: java.math.BigInteger): Unit {
    if (x < zero) { throw IllegalStateException() }
}

fun iterative(x: java.math.BigInteger): java.math.BigInteger {
    validate(x)
    var total = one
    for (i in two rangeTo x) { total *= i }
    return total
}
fun iterative(x: Int): java.math.BigInteger = iterative(x.bigint)
fun iterative(x: Long): java.math.BigInteger = iterative(x.bigint)

fun reductive(x: java.math.BigInteger): java.math.BigInteger {
    validate(x)
    if (x < two) { return one
    }
    return (one rangeTo x).reduce { t, i -> t * i }
}
fun reductive(x: Int): java.math.BigInteger = reductive(x.bigint)
fun reductive(x: Long): java.math.BigInteger = reductive(x.bigint)

fun foldive(x: java.math.BigInteger): java.math.BigInteger {
    validate(x)
    return (two rangeTo x).fold(one) { t, i -> t * i }
}
fun foldive(x: Int): java.math.BigInteger = foldive(x.bigint)
fun foldive(x: Long): java.math.BigInteger = foldive(x.bigint)

fun naïve_recursive(x: java.math.BigInteger): java.math.BigInteger {
    validate(x)
    if (x < two) {
        return one
    }
    return x * naïve_recursive(x - one)
}
fun naïve_recursive(x: Int): java.math.BigInteger = naïve_recursive(x.bigint)
fun naïve_recursive(x: Long): java.math.BigInteger = naïve_recursive(x.bigint)

// Use snake case to avoid conflict with Kotlin's tailRecursive function.
fun tail_recursive(x: java.math.BigInteger): java.math.BigInteger {
    validate(x)
    tailrec fun iterate(n: java.math.BigInteger, t: java.math.BigInteger = one): java.math.BigInteger {
        if (n < two) { return t }
        return iterate(n - one, n * t)
    }
    return iterate(x)
}
fun tail_recursive(x: Int): java.math.BigInteger = tail_recursive(x.bigint)
fun tail_recursive(x: Long): java.math.BigInteger = tail_recursive(x.bigint)