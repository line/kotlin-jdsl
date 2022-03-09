package com.linecorp.kotlinjdsl.spring.data.reactive.query

import com.linecorp.kotlinjdsl.query.creator.StageReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.SpringDataReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.reactive.SpringDataReactiveQueryFactoryImpl
import com.linecorp.kotlinjdsl.subquery
import kotlinx.coroutines.future.await
import org.hibernate.reactive.stage.Stage
import java.util.concurrent.CompletionStage

class SpringDataHibernateStageReactiveQueryFactory(
    private val sessionFactory: Stage.SessionFactory,
    private val subqueryCreator: SubqueryCreator
) {
    suspend fun <T> withFactory(block: (SpringDataReactiveQueryFactory) -> CompletionStage<T>): T =
        sessionFactory.withSession { session -> executeSessionWithFactory(session, block) }.await()

    suspend fun <T> transactionWithFactory(block: (SpringDataReactiveQueryFactory) -> CompletionStage<T>): T =
        sessionFactory.withTransaction { session -> executeSessionWithFactory(session, block) }.await()

    fun <T> subquery(classType: Class<T>, dsl: SubqueryDsl<T>.() -> Unit) =
        subquery(classType, subqueryCreator, dsl)

    fun <T> executeSessionWithFactory(session: Stage.Session, block: (SpringDataReactiveQueryFactory) -> CompletionStage<T>) =
        with(
            SpringDataReactiveQueryFactoryImpl(
                subqueryCreator = subqueryCreator,
                criteriaQueryCreator = StageReactiveCriteriaQueryCreator(sessionFactory.criteriaBuilder, session)
            ), block
        )
}
