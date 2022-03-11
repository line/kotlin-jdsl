package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.ReactiveQueryFactoryImpl
import com.linecorp.kotlinjdsl.query.creator.MutinyReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.subquery
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import java.util.concurrent.CompletionStage
import java.util.function.Supplier

class HibernateMutinyReactiveQueryFactory(
    private val sessionFactory: SessionFactory,
    private val subqueryCreator: SubqueryCreator,
) {
    suspend fun <T> withFactory(block: (ReactiveQueryFactory) -> CompletionStage<T>): T =
        sessionFactory.withSession { session -> uni { executeSessionWithFactory(session, block) } }
            .awaitSuspending()

    suspend fun <T> transactionWithFactory(block: (ReactiveQueryFactory) -> CompletionStage<T>): T =
        sessionFactory.withTransaction { session -> uni { executeSessionWithFactory(session, block) } }
            .awaitSuspending()

    fun <T> subquery(classType: Class<T>, dsl: SubqueryDsl<T>.() -> Unit) = subquery(classType, subqueryCreator, dsl)

    fun <T> executeSessionWithFactory(
        session: Mutiny.Session,
        block: (ReactiveQueryFactory) -> CompletionStage<T>
    ): CompletionStage<T> =
        with(
            ReactiveQueryFactoryImpl(
                subqueryCreator = subqueryCreator,
                criteriaQueryCreator = MutinyReactiveCriteriaQueryCreator(sessionFactory.criteriaBuilder, session)
            ), block
        )

    private fun <T> uni(stageSupplier: Supplier<CompletionStage<T>>): Uni<T> {
        return Uni.createFrom().completionStage(stageSupplier)
    }
}
