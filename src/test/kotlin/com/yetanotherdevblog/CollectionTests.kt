package com.yetanotherdevblog

import com.yetanotherdevblog.petclinic.model.Owner
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class CollectionTests {

    val orderedList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val unOrderedList = mutableListOf(orderedList).shuffle()


    @Test
    fun `any`() {
        assertTrue(orderedList.any())
        assertFalse(orderedList.any( { it == 0 } ))
    }

    @Test
    fun `all`() {
        assertTrue(orderedList.all( { it > 0 } ))
        assertFalse(orderedList.all( { it > 10 } ))
    }

    @Test
    fun `count`() {
        assertEquals(10, orderedList.count())
        assertEquals(0, orderedList.count( { it > 10 } ))
    }

    @Test
    fun `fold`() {
        assertEquals(55, orderedList.fold(0) {
            acc, next -> acc + next
        })
        assertEquals(97, orderedList.fold (42)  {
            acc, next -> acc + next
        })

        // compress list of chars
        assertEquals("abcdefgh".toList(),
                "aaaabbbbcccddddeeeeffffggghh".toList()
                        .fold(emptyList<Char>()) { result, value ->
                            if (result.isNotEmpty() && result.last() == value) result
                            else result + value
                        })
    }

    @Test
    fun `foldRight`() {
        // compress list of chars and reverse
        assertEquals("hgfedcba".toList(),
                "aaaabbbbcccddddeeeeffffggghh".toList()
                        .foldRight(emptyList<Char>(), { value: Char, result: List<Char> ->
                            if (result.isNotEmpty() && result.last() == value) result
                            else result + value
                        }))
    }

    @Test
    fun `forEach`() {
        orderedList.forEach { println(it) }
        orderedList.forEachIndexed { index, i -> println("$index -> $i") }
        unOrderedList.forEachIndexed { index, i -> println("$index -> $i") }
    }

    @Test
    fun `max`() {
        assertEquals(orderedList.last(), orderedList.max())
        assertEquals(orderedList.first(), orderedList.maxBy({ -it}))
    }

    @Test
    fun `min`() {
        assertEquals(orderedList.first(), orderedList.min())
        assertEquals(orderedList.last(), orderedList.minBy({ -it}))
    }

    @Test
    fun `none`() {
        assertTrue(orderedList.none { it == Int.MAX_VALUE })
    }

    @Test
    fun `reduce,reduceRight`() {
        assertEquals(55, orderedList.reduce { total, next -> total + next })
        assertEquals(10, listOf(1, 2, 3, 4).reduceRight { total, next -> total + next })

    }

    // Filtering

    @Test
    fun `drop`() {
        orderedList.drop(1)
    }

    @Test
    fun `dropWhile`() {
        orderedList.dropWhile { it < 10 }
    }

    @Test
    fun `dropLastWhile`() {
        orderedList.dropLastWhile { it < 10 }
    }


    @Test
    fun `filter`() {
        orderedList.filter { it < 10 }
        orderedList.filterNot { it < 10 }
        orderedList.filterNotNull()
        orderedList.filterIsInstance(Owner::class.java)
    }

    @Test
    fun `slice`() {
        orderedList.slice(1..3)
        orderedList.slice(listOf(1, 2, 3))
    }

    @Test
    fun `take`() {
        orderedList.take(2)
        orderedList.takeLast(2)
        orderedList.takeWhile { it > 0 }
        orderedList.takeLastWhile { it > 0 }
    }

    // mapping functions

//    @Test
//    fun `flatMap`() {
//
//        assertEquals(listOf(1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7),
//        list.flatMap { listOf(it, it + 1) })
//
//    }
//    ```
//
//    ### groupBy
//
//    Returns a map of the elements in original collection grouped by the result of given
//    function
//
//    ```kotlin
//    assertEquals(mapOf("odd" to listOf(1, 3, 5), "even" to listOf(2, 4, 6)),
//    list.groupBy { if (it % 2 == 0) "even" else "odd" })
//    ```
//
//    ### map
//
//    Returns a list containing the results of applying the given transform function to each
//    element of the original collection.
//
//    ```kotlin
//    assertEquals(listOf(2, 4, 6, 8, 10, 12), list.map { it * 2 })
//    ```
//
//    ### mapIndexed
//
//    Returns a list containing the results of applying the given transform function to each
//    element and its index of the original collection.
//
//    ```kotlin
//    assertEquals(listOf (0, 2, 6, 12, 20, 30), list.mapIndexed { index, it
//        -> index * it })
//    ```
//
//    ### mapNotNull
//
//    Returns a list containing the results of applying the given transform function to each
//    non-null element of the original collection.
//
//    ```kotlin
//    assertEquals(listOf(2, 4, 6, 8), listWithNull.mapNotNull { it * 2 })

}

private fun <E> MutableList<E>.shuffle(): MutableList<E> {
    val rg : Random = Random()
    for (i in 0..this.count() - 1) {
        val randomPosition = rg.nextInt(this.size)
        val tmp : E = this[i]
        this[i] = this[randomPosition]
        this[randomPosition] = tmp
    }
    return this
}

