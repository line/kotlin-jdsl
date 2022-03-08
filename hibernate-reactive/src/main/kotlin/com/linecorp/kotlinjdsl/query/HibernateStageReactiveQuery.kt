package com.linecorp.kotlinjdsl.query

import org.hibernate.reactive.stage.Stage
import java.util.concurrent.CompletionStage
import javax.persistence.Parameter
import kotlin.reflect.KClass

class HibernateStageReactiveQuery<R>(private val query: Stage.Query<R>) : ReactiveQuery<R> {
    override val singleResult: CompletionStage<R>
        get() = query.singleResult
    override val resultList: CompletionStage<List<R>>
        get() = query.resultList
    override val singleResultOrNull: CompletionStage<R?>
        get() = query.singleResultOrNull
    override val executeUpdate: CompletionStage<Int>
        get() = query.executeUpdate()

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
        // hint not supported in hibernate-reactive
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
