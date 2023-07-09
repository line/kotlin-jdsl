package com.linecorp.kotlinjdsl.dsl

import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

@JvmName("owner1")
fun <T : Any> KProperty1<T, *>.owner(): KClass<T> {
    @Suppress("UNCHECKED_CAST")
    return (this as CallableReference).owner as KClass<T>
}

@JvmName("owner2")
fun KProperty1<*, *>.owner(): KClass<*> {
    return (this as CallableReference).owner as KClass<*>
}
