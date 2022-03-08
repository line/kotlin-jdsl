package com.linecorp.kotlinjdsl.test.reactive

import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.query.HibernateStageReactiveQueryFactory
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import org.hibernate.reactive.stage.Stage.SessionFactory
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletionStage

@ExtendWith(StageSessionFactoryExtension::class)
interface HibernateCriteriaIntegrationTest : CriteriaQueryDslIntegrationTest<SessionFactory> {
    override suspend fun persist(entity: Any) {
        val log = LoggerFactory.getLogger(this::class.java)
        try {
            factory.withSession { session ->
                session.persist(entity).thenCompose { session.flush() }
            }.await()
        } catch (e: Exception) {
            log.warn(e.message, e)
            when (getRootCause(e)) {
                // retry(with delay 1s) below errors occur
                // java.lang.IllegalStateException: HR000065: No Vert.x context active
                // javax.persistence.PersistenceException: org.hibernate.HibernateException: java.lang.NullPointerException: Cannot invoke "io.vertx.sqlclient.RowSet.iterator()" because "rows" is null
                is NullPointerException, is IllegalStateException -> {
                    delay(1000)
                    persist(entity)
                }
                else -> {
                    throw e
                }
            }
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

    override suspend fun <T> withFactory(block: (ReactiveQueryFactory) -> CompletionStage<T>): T =
        HibernateStageReactiveQueryFactory(
            sessionFactory = factory,
            subqueryCreator = SubqueryCreatorImpl()
        ).withFactory(block)

    private fun getRootCause(throwable: Throwable): Throwable {
        var rootCause: Throwable = throwable
        while (rootCause.cause != null && rootCause.cause !== rootCause) {
            rootCause = rootCause.cause!!
        }
        return rootCause
    }
}
