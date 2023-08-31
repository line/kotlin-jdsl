package com.linecorp.kotlinjdsl.example.eclipselink

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.eclipselink.entity.publisher.Publisher
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import jakarta.persistence.Persistence
import org.eclipse.persistence.jpa.JpaEntityManager
import org.junit.jupiter.api.Test
import com.linecorp.kotlinjdsl.support.eclipselink.extension.createQuery

class SelectExample {
    private val entityManagerFactory = Persistence.createEntityManagerFactory("example")
    @Test
    fun test() {
        val entityManger = entityManagerFactory.createEntityManager().unwrap(JpaEntityManager::class.java)
        val transaction = entityManger.transaction

        transaction.begin()
        entityManger.persist(
            Publisher(
                publisherId = 1,
                name = "test",
            )
        )
        transaction.commit()

        val context = JpqlRenderContext()

        val query = jpql {
            select(
                path(Publisher::name),
            ).from(
                entity(Publisher::class),
            )
        }

        val typedQuery = entityManger.createQuery(query, context)

        val result = typedQuery.resultList
        println(result)
    }
}
