package com.linecorp.kotlinjdsl.query

import jakarta.persistence.Parameter
import kotlin.reflect.KClass

interface ReactiveQuery<R> {
    suspend fun singleResult(): R
    suspend fun resultList(): List<R>
    suspend fun singleResultOrNull(): R?
    suspend fun executeUpdate(): Int
    fun setParameter(position: Int, value: Any?): ReactiveQuery<R>
    fun setParameter(name: String, value: Any?): ReactiveQuery<R>
    fun <T> setParameter(parameter: Parameter<T>, value: T?): ReactiveQuery<R>
    fun setMaxResults(maxResults: Int): ReactiveQuery<R>
    fun setFirstResult(firstResult: Int): ReactiveQuery<R>
    fun setQueryHint(hintName: String, value: Any)
    val maxResults: Int
    val firstResult: Int
    fun <T : Any> unwrap(type: KClass<T>): T
}
