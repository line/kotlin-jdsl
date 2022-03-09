package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.subquery
import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.ReactiveQueryFactoryImpl
import com.linecorp.kotlinjdsl.query.creator.StageReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import kotlinx.coroutines.future.await
import org.hibernate.reactive.stage.Stage
import org.hibernate.reactive.stage.Stage.SessionFactory
import java.util.concurrent.CompletionStage

class HibernateStageReactiveQueryFactory(
    private val sessionFactory: SessionFactory,
    private val subqueryCreator: SubqueryCreator,
) {
    suspend fun <T> withFactory(block: (ReactiveQueryFactory) -> CompletionStage<T>): T =
        sessionFactory.withSession { session -> executeSessionWithFactory(session, block) }.await()

    suspend fun <T> transactionWithFactory(block: (ReactiveQueryFactory) -> CompletionStage<T>): T =
        sessionFactory.withTransaction { session -> executeSessionWithFactory(session, block) }.await()

    fun <T> subquery(classType: Class<T>, dsl: SubqueryDsl<T>.() -> Unit) = subquery(classType, subqueryCreator, dsl)

    fun <T> executeSessionWithFactory(session: Stage.Session, block: (ReactiveQueryFactory) -> CompletionStage<T>) =
        with(
            ReactiveQueryFactoryImpl(
                subqueryCreator = subqueryCreator,
                criteriaQueryCreator = StageReactiveCriteriaQueryCreator(sessionFactory.criteriaBuilder, session)
            ), block
        )
}
