package com.linecorp.kotlinjdsl.querydsl.from

import kotlin.reflect.KProperty1

interface RelationDsl {
    fun <T, R> on(property: KProperty1<T, R>): Relation<T, R> = Relation(property.name)

    @Suppress("INAPPLICABLE_JVM_NAME")
    @JvmName("onCollection")
    fun <T, R> on(property: KProperty1<T, Collection<R>>): Relation<T, R> = Relation(property.name)
}

