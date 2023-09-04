package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.JpqlEntityManagerUtils
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.IncorrectResultSizeDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.NonUniqueResultException

@Transactional
@NoRepositoryBean
@SinceJdsl("3.0.0")
open class KotlinJdslJpqlExecutorImpl(
    private val entityManager: EntityManager,
    private val renderContext: RenderContext,
) : KotlinJdslJpqlExecutor {
    override fun <T : Any> findFirst(init: Jpql.() -> JpqlQueryable<SelectQuery<T>>): T {
        return findFirst(Jpql, init)
    }

    override fun <T : Any, DSL : JpqlDsl> findFirst(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): T {
        return findFirstOrNull(dsl, init)
            ?: throw EmptyResultDataAccessException("Result must not be null", 1)
    }

    override fun <T : Any> findFirstN(
        n: Int,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T> {
        return findFirstN(Jpql, n, init)
    }

    override fun <T : Any, DSL : JpqlDsl> findFirstN(
        dsl: JpqlDsl.Constructor<DSL>,
        n: Int,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T> {
        val query: SelectQuery<T> = jpql(dsl, init)
        val jpqQuery = JpqlEntityManagerUtils.createQuery(entityManager, query, renderContext)

        return jpqQuery.setMaxResults(n).resultList
            .takeIf { it.isNotEmpty() }
            ?: throw EmptyResultDataAccessException("Result must not be empty", 1)
    }

    override fun <T : Any> findFirstOrNull(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): T? {
        return findFirstOrNull(Jpql, init)
    }

    override fun <T : Any, DSL : JpqlDsl> findFirstOrNull(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): T? {
        val query: SelectQuery<T> = jpql(dsl, init)
        val jpqQuery = JpqlEntityManagerUtils.createQuery(entityManager, query, renderContext)

        val results = jpqQuery.setMaxResults(1).resultList

        return results.firstOrNull()
    }

    override fun <T : Any> findOne(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): T? {
        return findOne(Jpql, init)
    }

    override fun <T : Any, DSL : JpqlDsl> findOne(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): T? {
        val query: SelectQuery<T> = jpql(dsl, init)
        val jpa = JpqlEntityManagerUtils.createQuery(entityManager, query, renderContext)

        return try {
            jpa.setMaxResults(2).singleResult
        } catch (e: NonUniqueResultException) {
            @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
            throw IncorrectResultSizeDataAccessException(e.message, 1, e)
        } catch (e: NoResultException) {
            null
        }
    }

    override fun <T : Any> findAll(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> {
        return findAll(dsl = Jpql, init = init)
    }

    override fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> {
        val query: SelectQuery<T> = jpql(dsl, init)
        val jpaQuery = JpqlEntityManagerUtils.createQuery(entityManager, query, renderContext)

        return jpaQuery.resultList
    }

    override fun <T : Any> findAll(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?> {
        return findAll(Jpql, pageable, init)
    }

    override fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?> {
        val query: SelectQuery<T> = jpql(dsl, init)

        return JpqlEntityManagerUtils.queryForPage(entityManager, query, pageable, renderContext)
    }

    override fun <T : Any> update(
        init: Jpql.() -> JpqlQueryable<UpdateQuery<T>>,
    ): Int {
        return update(Jpql, init)
    }

    override fun <T : Any, DSL : JpqlDsl> update(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<UpdateQuery<T>>,
    ): Int {
        val query: UpdateQuery<T> = jpql(dsl, init)
        val jpaQuery = JpqlEntityManagerUtils.createQuery(entityManager, query, renderContext)

        return jpaQuery.executeUpdate()
    }

    override fun <T : Any> delete(
        init: Jpql.() -> JpqlQueryable<DeleteQuery<T>>,
    ): Int {
        return delete(Jpql, init)
    }

    override fun <T : Any, DSL : JpqlDsl> delete(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<DeleteQuery<T>>,
    ): Int {
        val query: DeleteQuery<T> = jpql(dsl, init)
        val jpaQuery = JpqlEntityManagerUtils.createQuery(entityManager, query, renderContext)

        return jpaQuery.executeUpdate()
    }
}
