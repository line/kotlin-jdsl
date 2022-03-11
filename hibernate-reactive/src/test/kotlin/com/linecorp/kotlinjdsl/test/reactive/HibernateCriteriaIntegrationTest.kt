package com.linecorp.kotlinjdsl.test.reactive

import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.query.HibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletionStage
import kotlin.reflect.KClass

@ExtendWith(MutinySessionFactoryExtension::class)
interface HibernateCriteriaIntegrationTest : CriteriaQueryDslIntegrationTest<Mutiny.SessionFactory> {
    override suspend fun persist(entity: Any) {
        entityManagerFactory.createEntityManager().apply {
            transaction.apply {
                begin()
                persist(entity)
                flush()
                commit()
            }
            close()
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
                    .flatMap { session.remove(it) }
                    .flatMap { session.flush() }
            }.awaitSuspending()
        }
    }

    override suspend fun removeAll(entities: Collection<Any>) {
        removeAll(entities.toTypedArray())
    }
    override suspend fun <T> withFactory(block: (ReactiveQueryFactory) -> CompletionStage<T>): T =
        HibernateMutinyReactiveQueryFactory(
            sessionFactory = factory,
            subqueryCreator = SubqueryCreatorImpl()
        ).withFactory(block)
}

private val log = LoggerFactory.getLogger(HibernateCriteriaIntegrationTest::class.java)

suspend fun retry(maxTries: Long = 0, delayInMillis: Long = 100, retryExceptions: List<KClass<*>>, block: suspend () -> Unit) {
    runCatching {
        block()
    }.onFailure {
        if (maxTries > 0 && it::class in retryExceptions) {
            log.warn("Exception occured, retry remain : ${maxTries - 1}", it)
            delay(delayInMillis)
            retry(maxTries - 1, delayInMillis, retryExceptions, block)
        }
    }
}
