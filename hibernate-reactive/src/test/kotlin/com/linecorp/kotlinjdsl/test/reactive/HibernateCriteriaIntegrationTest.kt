package com.linecorp.kotlinjdsl.test.reactive

import com.linecorp.kotlinjdsl.ReactiveQueryFactory
import com.linecorp.kotlinjdsl.query.HibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.extension.ExtendWith

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

    override suspend fun <T> withFactory(block: suspend (ReactiveQueryFactory) -> T): T =
        HibernateMutinyReactiveQueryFactory(
            sessionFactory = factory,
            subqueryCreator = SubqueryCreatorImpl()
        ).withFactory(block)
}
