package com.linecorp.kotlinjdsl.query

import java.util.concurrent.CompletionStage
import javax.persistence.Parameter
import kotlin.reflect.KClass

interface ReactiveQuery<R> {
    val singleResult: CompletionStage<R>
    val resultList: CompletionStage<List<R>>
    val singleResultOrNull: CompletionStage<R?>
    val executeUpdate: CompletionStage<Int>
    fun setParameter(position: Int, value: Any?): ReactiveQuery<R>
    fun setParameter(name: String, value: Any?): ReactiveQuery<R>
    fun <T> setParameter(parameter: Parameter<T>, value: T?): ReactiveQuery<R>
    fun setMaxResults(maxResults: Int): ReactiveQuery<R>
    fun setFirstResult(firstResult: Int): ReactiveQuery<R>
    fun setQueryHint(hintName: String, value: Any)
    val maxResults: Int
    val firstResult: Int
    fun <T: Any> unwrap(type: KClass<T>): T
}
