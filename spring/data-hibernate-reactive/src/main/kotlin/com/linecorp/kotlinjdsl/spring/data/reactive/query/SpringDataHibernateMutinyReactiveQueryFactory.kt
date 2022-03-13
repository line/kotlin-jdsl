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

class SpringDataHibernateMutinyReactiveQueryFactory(
    private val sessionFactory: Mutiny.SessionFactory, private val subqueryCreator: SubqueryCreator
) {
    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    suspend fun <T> withFactory(block: suspend (SpringDataReactiveQueryFactory) -> T): T =
        sessionFactory.withSession {
            makeFactory(it).let { GlobalScope.async(Dispatchers.Unconfined) { block(it) } }.asUni()
        }.awaitSuspending()

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    suspend fun <T> transactionWithFactory(block: suspend (SpringDataReactiveQueryFactory) -> T): T =
        sessionFactory.withTransaction { session ->
            makeFactory(session).let { GlobalScope.async(Dispatchers.Unconfined) { block(it) } }.asUni()
        }.awaitSuspending()

    fun <T> subquery(classType: Class<T>, dsl: SpringDataReactiveSubqueryDsl<T>.() -> Unit) =
        SubqueryExpressionSpec(
            spec = SpringDataReactiveReactiveQueryDslImpl(classType).apply(dsl).createSubquerySpec(),
            subqueryCreator = subqueryCreator
        )

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    fun <T> executeSessionWithFactory(
        session: Mutiny.Session,
        block: suspend (SpringDataReactiveQueryFactory) -> T
    ): Uni<T> =
        GlobalScope.async(Dispatchers.Unconfined) { block(makeFactory(session)) }.asUni()


    private fun makeFactory(it: Mutiny.Session) = SpringDataReactiveQueryFactoryImpl(
        subqueryCreator = subqueryCreator,
        criteriaQueryCreator = MutinyReactiveCriteriaQueryCreator(sessionFactory.criteriaBuilder, it)
    )
}
