package com.linecorp.kotlinjdsl

import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

fun KProperty1<*, *>.owner(): KClass<*> {
    return (this as CallableReference).owner as KClass<*>
}
