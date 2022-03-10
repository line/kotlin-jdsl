package com.linecorp.kotlinjdsl.test.reactive

import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.ReactiveQueryFactoryImpl
import com.linecorp.kotlinjdsl.query.creator.StageReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withContext
import org.hibernate.reactive.stage.Stage.SessionFactory
import org.junit.jupiter.api.extension.ExtendWith
import java.util.concurrent.CompletionStage

@ExtendWith(StageSessionFactoryExtension::class)
interface HibernateCriteriaIntegrationTest : CriteriaQueryDslIntegrationTest<SessionFactory> {
    override suspend fun persist(entity: Any) {
        retryPersist {
            factory.withSession { session ->
                session.persist(entity).thenCompose { session.flush() }
            }.await()
        }
    }

    override suspend fun persistAll(vararg entities: Any) {
        entities.forEach { entity -> persist(entity) }
    }

    override suspend fun persistAll(entities: Collection<Any>) {
        persistAll(entities.toTypedArray())
    }

    override suspend fun removeAll(vararg entities: Any) {
        entities.forEach { entity ->
            factory.withSession { session ->
                session.merge(entity)
                    .thenCompose { session.remove(it) }
                    .thenCompose { session.flush() }
            }.await()
        }
    }

    override suspend fun removeAll(entities: Collection<Any>) {
        removeAll(entities.toTypedArray())
    }

    override suspend fun <T> withFactory(block: (ReactiveQueryFactory) -> CompletionStage<T>): T {
        // override suspend fun <T> withFactory(block: (ReactiveQueryFactory) -> CompletionStage<T>): T =
        //        HibernateStageReactiveQueryFactory(
        //            sessionFactory = factory,
        //            subqueryCreator = SubqueryCreatorImpl()
        //        ).withFactory(block)
        return factory.withSession {
                with(
                    ReactiveQueryFactoryImpl(
                        subqueryCreator = SubqueryCreatorImpl(),
                        criteriaQueryCreator = StageReactiveCriteriaQueryCreator(factory.criteriaBuilder, it)
                    ), block
                )
            }.toCompletableFuture().get()
    }
}

suspend fun retryPersist(block: suspend FlowCollector<Any>.() -> Unit) {
    flow(block).retry(10).collect {}
}
