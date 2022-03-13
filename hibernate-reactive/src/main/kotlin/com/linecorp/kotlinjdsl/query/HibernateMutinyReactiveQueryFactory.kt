package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.ReactiveQueryFactoryImpl
import com.linecorp.kotlinjdsl.query.creator.MutinyReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.MutinyStatelessReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.subquery
import io.smallrye.mutiny.coroutines.asUni
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.*
import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import kotlin.coroutines.CoroutineContext

class HibernateMutinyReactiveQueryFactory(
    /**
     * When methods such as withSession and withTransaction are executed, the scope responsible for the actual DB processing must always be executed in the thread where the withXXX method is executed.
     * For this, we will use Unconfined Dispatcher by default.
     * However, we prevent hard-coding the dispatcher and inject and process executeQueryContext so that we can change it to a separate CoroutineContext when we want to change it.
     */
    private val executeQueryContext: CoroutineContext = Dispatchers.Unconfined,
    private val sessionFactory: SessionFactory,
    private val subqueryCreator: SubqueryCreator,
) {
    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun <T> withFactory(block: suspend (Mutiny.Session, ReactiveQueryFactory) -> T): T =
        sessionFactory.withSession { session -> makeFactory(session).let { makeScope().async { block(session, it) } }.asUni() }
            .awaitSuspending()

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun <T> statelessWithFactory(block: suspend (ReactiveQueryFactory) -> T): T =
        sessionFactory.withStatelessSession { makeFactory(it).let { makeScope().async { block(it) } }.asUni() }
            .awaitSuspending()

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun <T> withFactory(block: suspend (ReactiveQueryFactory) -> T): T =
        sessionFactory.withSession { makeFactory(it).let { makeScope().async { block(it) } }.asUni() }
            .awaitSuspending()

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun <T> transactionWithFactory(block: suspend (ReactiveQueryFactory) -> T): T =
        sessionFactory.withTransaction { session -> makeFactory(session).let { makeScope().async { block(it) } }.asUni() }
            .awaitSuspending()

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun <T> transactionWithFactory(block: suspend (Mutiny.Session, ReactiveQueryFactory) -> T): T =
        sessionFactory.withTransaction { session -> makeFactory(session).let { makeScope().async { block(session, it) } }.asUni() }
            .awaitSuspending()

    fun <T> subquery(classType: Class<T>, dsl: SubqueryDsl<T>.() -> Unit) = subquery(classType, subqueryCreator, dsl)

    private fun makeScope() = CoroutineScope(SupervisorJob() + executeQueryContext)

    private fun makeFactory(it: Mutiny.Session) = ReactiveQueryFactoryImpl(
        subqueryCreator = subqueryCreator,
        criteriaQueryCreator = MutinyReactiveCriteriaQueryCreator(sessionFactory.criteriaBuilder, it)
    )

    private fun makeFactory(it: Mutiny.StatelessSession) = ReactiveQueryFactoryImpl(
        subqueryCreator = subqueryCreator,
        criteriaQueryCreator = MutinyStatelessReactiveCriteriaQueryCreator(sessionFactory.criteriaBuilder, it)
    )
}
