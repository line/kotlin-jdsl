package com.linecorp.kotlinjdsl.support.spring.batch.item.database.orm

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.batch.JpqlEntityManagerUtils
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import org.springframework.batch.item.database.orm.JpaQueryProvider

class KotlinJdslQueryProvider<T : Any>(
    private val query: SelectQuery<T>,
    private val queryParams: Map<String, Any?>,
    private val context: RenderContext,
) : JpaQueryProvider {
    private var entityManager: EntityManager? = null

    override fun createQuery(): Query {
        if (entityManager == null) {
            throw IllegalStateException("There is no entityManager. Please set entityManager before create query.")
        }
        return JpqlEntityManagerUtils.createQuery(entityManager!!, query, queryParams, context)
    }

    override fun setEntityManager(entityManager: EntityManager) {
        this.entityManager = entityManager
    }
}
