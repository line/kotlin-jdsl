package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.ReactiveQueryFactoryImpl
import com.linecorp.kotlinjdsl.query.creator.MutinyReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.subquery
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.asUni
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.*
import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory

class HibernateMutinyReactiveQueryFactory(
    private val sessionFactory: SessionFactory,
    private val subqueryCreator: SubqueryCreator,
) {
    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    suspend fun <T> withFactory(block: suspend (ReactiveQueryFactory) -> T): T =
        sessionFactory.withSession {
            makeFactory(it).let { GlobalScope.async(Dispatchers.Unconfined) { block(it) } }.asUni()
        }.awaitSuspending()

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    suspend fun <T> transactionWithFactory(block: suspend (ReactiveQueryFactory) -> T): T =
        sessionFactory.withTransaction { session ->
            makeFactory(session).let { GlobalScope.async(Dispatchers.Unconfined) { block(it) } }.asUni()
        }.awaitSuspending()

    fun <T> subquery(classType: Class<T>, dsl: SubqueryDsl<T>.() -> Unit) = subquery(classType, subqueryCreator, dsl)

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    fun <T> executeSessionWithFactory(
        session: Mutiny.Session,
        block: suspend (ReactiveQueryFactory) -> T
    ): Uni<T> =
        GlobalScope.async(Dispatchers.Unconfined) { block(makeFactory(session)) }.asUni()

    private fun makeFactory(it: Mutiny.Session) = ReactiveQueryFactoryImpl(
        subqueryCreator = subqueryCreator,
        criteriaQueryCreator = MutinyReactiveCriteriaQueryCreator(sessionFactory.criteriaBuilder, it)
    )
}
