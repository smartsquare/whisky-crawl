package de.smartsquare.whisky.util

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

fun <A, B> Iterable<A>.parallelFlatMap(mapper: suspend (A) -> Iterable<B>): List<B> = runBlocking {
    map { async(CommonPool) { mapper(it) } }.flatMap { it.await() }
}
