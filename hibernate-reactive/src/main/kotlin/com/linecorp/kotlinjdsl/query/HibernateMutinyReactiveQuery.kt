package com.linecorp.kotlinjdsl.query

import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny
import javax.persistence.Parameter
import kotlin.reflect.KClass

class HibernateMutinyReactiveQuery<R>(private val query: Mutiny.Query<R>) : ReactiveQuery<R> {
    override suspend fun singleResult(): R = query.singleResult.awaitSuspending()
    override suspend fun resultList(): List<R> = query.resultList.awaitSuspending()
    override suspend fun singleResultOrNull(): R? = query.singleResultOrNull.awaitSuspending()
    override suspend fun executeUpdate(): Int = query.executeUpdate().awaitSuspending()

    override fun setParameter(position: Int, value: Any?): ReactiveQuery<R> {
        query.setParameter(position, value)
        return this
    }

    override fun setParameter(name: String, value: Any?): ReactiveQuery<R> {
        query.setParameter(name, value)
        return this
    }

    override fun <T> setParameter(parameter: Parameter<T>, value: T?): ReactiveQuery<R> {
        query.setParameter(parameter, value)
        return this
    }

    override fun setMaxResults(maxResults: Int): ReactiveQuery<R> {
        query.maxResults = maxResults
        return this
    }

    override fun setFirstResult(firstResult: Int): ReactiveQuery<R> {
        query.firstResult = firstResult
        return this
    }
    override fun setQueryHint(hintName: String, value: Any) {
        throw UnsupportedOperationException("Hibernate-reactive does not support JPA query hint yet. if hibernate-reactive setQueryHint method support officially please let us know. we will fix it")
    }

    override val maxResults: Int
        get() = query.maxResults
    override val firstResult: Int
        get() = query.firstResult

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> unwrap(type: KClass<T>): T {
        return query as T
    }
}
