package com.linecorp.kotlinjdsl.spring.data.reactive.query

import com.linecorp.kotlinjdsl.query.creator.MutinyReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.spring.reactive.SpringDataReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.reactive.SpringDataReactiveQueryFactoryImpl
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.SpringDataReactiveReactiveQueryDslImpl
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.SpringDataReactiveSubqueryDsl
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.asUni
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.*
import org.hibernate.reactive.mutiny.Mutiny
import kotlin.coroutines.CoroutineContext

class SpringDataHibernateMutinyReactiveQueryFactory(
    /**
     * When methods such as withSession and withTransaction are executed, the scope responsible for the actual DB processing must always be executed in the thread where the withXXX method is executed.
     * For this, we will use Unconfined Dispatcher by default.
     * However, we prevent hard-coding the dispatcher and inject and process executeQueryContext so that we can change it to a separate CoroutineContext when we want to change it.
     */
    private val executeQueryContext: CoroutineContext = Dispatchers.Unconfined,
    private val sessionFactory: Mutiny.SessionFactory,
    private val subqueryCreator: SubqueryCreator
) {
    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun <T> withFactory(block: suspend (SpringDataReactiveQueryFactory) -> T): T =
        sessionFactory.withSession { makeFactory(it).let { makeScope().async { block(it) } }.asUni() }
            .awaitSuspending()

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun <T> transactionWithFactory(block: suspend (SpringDataReactiveQueryFactory) -> T): T =
        sessionFactory.withTransaction { session -> makeFactory(session).let { makeScope().async { block(it) } }.asUni() }
            .awaitSuspending()

    fun <T> subquery(classType: Class<T>, dsl: SpringDataReactiveSubqueryDsl<T>.() -> Unit) =
        SubqueryExpressionSpec(
            spec = SpringDataReactiveReactiveQueryDslImpl(classType).apply(dsl).createSubquerySpec(),
            subqueryCreator = subqueryCreator
        )

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(ExperimentalCoroutinesApi::class)
    fun <T> executeSessionWithFactory(
        session: Mutiny.Session,
        block: suspend (SpringDataReactiveQueryFactory) -> T
    ): Uni<T> =
        makeScope().async { block(makeFactory(session)) }.asUni()

    private fun makeScope() = CoroutineScope(SupervisorJob() + executeQueryContext)

    private fun makeFactory(it: Mutiny.Session) = SpringDataReactiveQueryFactoryImpl(
        subqueryCreator = subqueryCreator,
        criteriaQueryCreator = MutinyReactiveCriteriaQueryCreator(sessionFactory.criteriaBuilder, it)
    )
}
