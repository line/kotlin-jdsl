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
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Transactional
@NoRepositoryBean
@SinceJdsl("3.0.0")
open class KotlinJdslJpqlExecutorImpl(
    private val entityManager: EntityManager,
    private val renderContext: RenderContext,
) : KotlinJdslJpqlExecutor {
    override fun <T : Any> findAll(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> {
        return findAll(Jpql, init)
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
    ): List<T?> {
        return findAll(Jpql, pageable, init)
    }

    override fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> {
        val query: SelectQuery<T> = jpql(dsl, init)

        return JpqlEntityManagerUtils.queryForList(entityManager, query, pageable, renderContext)
    }

    override fun <T : Any> findPage(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?> {
        return findPage(Jpql, pageable, init)
    }

    override fun <T : Any, DSL : JpqlDsl> findPage(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?> {
        val query: SelectQuery<T> = jpql(dsl, init)

        return JpqlEntityManagerUtils.queryForPage(entityManager, query, pageable, renderContext)
    }

    override fun <T : Any> findSlice(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Slice<T?> {
        return findSlice(Jpql, pageable, init)
    }

    override fun <T : Any, DSL : JpqlDsl> findSlice(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Slice<T?> {
        val query: SelectQuery<T> = jpql(dsl, init)

        return JpqlEntityManagerUtils.queryForSlice(entityManager, query, pageable, renderContext)
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
