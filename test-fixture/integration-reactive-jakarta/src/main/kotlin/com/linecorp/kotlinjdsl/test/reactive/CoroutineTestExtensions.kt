package com.linecorp.kotlinjdsl.test.reactive

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

fun runBlocking(context: CoroutineContext = Dispatchers.IO, block: suspend () -> Unit) {
    kotlinx.coroutines.runBlocking(context) {
        block()
    }
}
