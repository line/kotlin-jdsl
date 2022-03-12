package com.linecorp.kotlinjdsl.query

import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.session.ReactiveQuery as HibernateReactiveQuery
import java.util.concurrent.CompletionStage
import javax.persistence.Parameter
import kotlin.reflect.KClass

class HibernateMutinyReactiveQuery<R>(private val query: Mutiny.Query<R>) : ReactiveQuery<R> {
    override val singleResult: CompletionStage<R>
        get() = query.singleResult.subscribeAsCompletionStage()
    override val resultList: CompletionStage<List<R>>
        get() = query.resultList.subscribeAsCompletionStage()
    override val singleResultOrNull: CompletionStage<R?>
        get() = query.singleResultOrNull.subscribeAsCompletionStage()
    override val executeUpdate: CompletionStage<Int>
        get() = query.executeUpdate().subscribeAsCompletionStage()

    private val exception = UnsupportedOperationException("Hibernate-reactive does not support JPA query hint yet.")

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

    /**
     * Hibernate Reactive's Mutiny.Query does not support setQueryHint method officially.
     * but org.hibernate.reactive.session.ReactiveQuery has a setQueryHint method.
     * Mutiny.Query (MutinyQueryImpl to be exact) has an org.hibernate.reactive.session.ReactiveQuery instance
     * to delegate to ReactiveQuery methods.
     * So we support setQueryHint by calling ReactiveQuery.setQueryHint Method included in Mutiny.Query.
     * This reflection call will be removed in the future to officially support setQueryHint in Mutiny.Query.
     * @see org.hibernate.reactive.session.ReactiveQuery#setQueryHint
     */
    override fun setQueryHint(hintName: String, value: Any) {
        query::class.java.declaredFields
            .find { it.type.name == HibernateReactiveQuery::class.qualifiedName }
            ?.run {
                isAccessible = true
                (get(query) as HibernateReactiveQuery<*>).setQueryHint(hintName, value)
                    ?: throw exception
            } ?: throw exception
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