package com.linecorp.kotlinjdsl.spring.data.reactive.query

import com.linecorp.kotlinjdsl.query.creator.MutinyReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.SpringDataReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.reactive.SpringDataReactiveQueryFactoryImpl
import com.linecorp.kotlinjdsl.subquery
import io.smallrye.mutiny.Uni
import kotlinx.coroutines.future.await
import org.hibernate.reactive.mutiny.Mutiny
import java.util.concurrent.CompletionStage
import java.util.function.Supplier

class SpringDataHibernateMutinyReactiveQueryFactory(
    private val sessionFactory: Mutiny.SessionFactory, private val subqueryCreator: SubqueryCreator
) {
    suspend fun <T> withFactory(block: (SpringDataReactiveQueryFactory) -> CompletionStage<T>): T =
        sessionFactory.withSession { session -> executeSessionWithFactory(session, block) }
            .subscribeAsCompletionStage().await()

    suspend fun <T> transactionWithFactory(block: (SpringDataReactiveQueryFactory) -> CompletionStage<T>): T =
        sessionFactory.withTransaction { session -> executeSessionWithFactory(session, block) }
            .subscribeAsCompletionStage().await()

    fun <T> subquery(classType: Class<T>, dsl: SubqueryDsl<T>.() -> Unit) = subquery(classType, subqueryCreator, dsl)

    fun <T> executeSessionWithFactory(
        session: Mutiny.Session, block: (SpringDataReactiveQueryFactory) -> CompletionStage<T>
    ): Uni<T> = uni {
        with(
            SpringDataReactiveQueryFactoryImpl(
                subqueryCreator = subqueryCreator,
                criteriaQueryCreator = MutinyReactiveCriteriaQueryCreator(sessionFactory.criteriaBuilder, session)
            ), block
        )
    }

}

fun <T> uni(stageSupplier: Supplier<CompletionStage<T>>): Uni<T> {
    return Uni.createFrom().completionStage(stageSupplier)
}
