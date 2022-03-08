package com.linecorp.kotlinjdsl.test.reactive

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun runBlocking(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> Unit) {
    kotlinx.coroutines.runBlocking(context) {
        block()
    }
}
