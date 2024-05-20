@file:Suppress("SqlSourceToSinkFlow")

package com.linecorp.kotlinjdsl.support.spring.data.jpa.repository

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.data.jpa.EnhancedTypedQuery
import com.linecorp.kotlinjdsl.support.spring.data.jpa.JpqlEntityManagerUtils
import jakarta.persistence.EntityManager
import jakarta.persistence.LockModeType
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.support.CrudMethodMetadata
import org.springframework.data.jpa.repository.support.QueryHints
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.support.PageableExecutionUtilsAdaptor
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@NoRepositoryBean
@SinceJdsl("3.0.0")
@Transactional(readOnly = true)
open class KotlinJdslJpqlExecutorImpl(
    private val entityManager: EntityManager,
    private val renderContext: RenderContext,
    private val metadata: CrudMethodMetadata?,
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
        val jpaQuery = createJpaQuery(query, query.returnType)

        return jpaQuery.resultList
    }

    override fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: DSL,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> {
        val query: SelectQuery<T> = jpql(dsl, init)
        val jpaQuery = createJpaQuery(query, query.returnType)

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

        return createList(query, query.returnType, pageable)
    }

    override fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: DSL,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> {
        val query: SelectQuery<T> = jpql(dsl, init)

        return createList(query, query.returnType, pageable)
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

        return createPage(query, query.returnType, pageable)
    }

    override fun <T : Any, DSL : JpqlDsl> findPage(
        dsl: DSL,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?> {
        val query: SelectQuery<T> = jpql(dsl, init)

        return createPage(query, query.returnType, pageable)
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

        return createSlice(query, query.returnType, pageable)
    }

    override fun <T : Any, DSL : JpqlDsl> findSlice(
        dsl: DSL,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Slice<T?> {
        val query: SelectQuery<T> = jpql(dsl, init)

        return createSlice(query, query.returnType, pageable)
    }

    @Transactional
    override fun <T : Any> update(
        init: Jpql.() -> JpqlQueryable<UpdateQuery<T>>,
    ): Int {
        return update(Jpql, init)
    }

    @Transactional
    override fun <T : Any, DSL : JpqlDsl> update(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<UpdateQuery<T>>,
    ): Int {
        val query: UpdateQuery<T> = jpql(dsl, init)
        val jpaQuery = createJpaQuery(query)

        return jpaQuery.executeUpdate()
    }

    @Transactional
    override fun <T : Any, DSL : JpqlDsl> update(
        dsl: DSL,
        init: DSL.() -> JpqlQueryable<UpdateQuery<T>>,
    ): Int {
        val query: UpdateQuery<T> = jpql(dsl, init)
        val jpaQuery = createJpaQuery(query)

        return jpaQuery.executeUpdate()
    }

    @Transactional
    override fun <T : Any> delete(
        init: Jpql.() -> JpqlQueryable<DeleteQuery<T>>,
    ): Int {
        return delete(Jpql, init)
    }

    @Transactional
    override fun <T : Any, DSL : JpqlDsl> delete(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<DeleteQuery<T>>,
    ): Int {
        val query: DeleteQuery<T> = jpql(dsl, init)
        val jpaQuery = createJpaQuery(query)

        return jpaQuery.executeUpdate()
    }

    @Transactional
    override fun <T : Any, DSL : JpqlDsl> delete(
        dsl: DSL,
        init: DSL.() -> JpqlQueryable<DeleteQuery<T>>,
    ): Int {
        val query: DeleteQuery<T> = jpql(dsl, init)
        val jpaQuery = createJpaQuery(query)

        return jpaQuery.executeUpdate()
    }

    private fun <T : Any> createJpaQuery(
        query: JpqlQuery<*>,
        returnType: KClass<T>,
    ): TypedQuery<T> {
        return JpqlEntityManagerUtils.createQuery(entityManager, query, returnType, renderContext).apply {
            setMetadata(this, metadata)
        }
    }

    private fun createJpaQuery(
        query: JpqlQuery<*>,
    ): Query {
        return JpqlEntityManagerUtils.createQuery(entityManager, query, renderContext).apply {
            setMetadata(this, metadata)
        }
    }

    private fun <T : Any> createJpaEnhancedQuery(
        query: JpqlQuery<*>,
        returnType: KClass<T>,
        sort: Sort,
    ): EnhancedTypedQuery<T> {
        val enhancedQuery = JpqlEntityManagerUtils.createEnhancedQuery(
            entityManager,
            query,
            returnType,
            sort,
            renderContext,
        )

        return EnhancedTypedQuery(
            enhancedQuery.sortedQuery.apply { setMetadata(this, metadata) },
        ) {
            // Lazy
            enhancedQuery.countQuery.apply { setMetadataForCount(this, metadata) }
        }
    }

    private fun setMetadata(query: Query, metadata: CrudMethodMetadata?) {
        if (metadata == null) return

        setLockMode(query, metadata.lockModeType)
        setQueryHints(query, metadata.queryHints)
    }

    private fun setMetadataForCount(query: Query, metadata: CrudMethodMetadata?) {
        if (metadata == null) return

        setQueryHints(query, metadata.queryHintsForCount)
    }

    private fun setLockMode(query: Query, lockMode: LockModeType?) {
        if (lockMode != null) {
            query.lockMode = lockMode
        }
    }

    private fun setQueryHints(query: Query, hints: QueryHints?) {
        hints?.forEach { name, value ->
            query.setHint(name, value)
        }
    }

    private fun <T : Any> createList(
        query: JpqlQuery<*>,
        returnType: KClass<T>,
        pageable: Pageable,
    ): List<T?> {
        val enhancedQuery = createJpaEnhancedQuery(query, returnType, pageable.sort)

        val sortedQuery = enhancedQuery.sortedQuery

        if (pageable.isPaged) {
            sortedQuery.firstResult = pageable.offset.toInt()
            sortedQuery.maxResults = pageable.pageSize
        }

        return sortedQuery.resultList
    }

    private fun <T : Any> createPage(
        query: JpqlQuery<*>,
        returnType: KClass<T>,
        pageable: Pageable,
    ): Page<T?> {
        val enhancedQuery = createJpaEnhancedQuery(query, returnType, pageable.sort)

        val sortedQuery = enhancedQuery.sortedQuery

        if (pageable.isPaged) {
            sortedQuery.firstResult = pageable.offset.toInt()
            sortedQuery.maxResults = pageable.pageSize
        }

        return PageableExecutionUtilsAdaptor.getPage(sortedQuery.resultList, pageable) {
            val counts = enhancedQuery.countQuery.resultList

            if (counts.size == 1) {
                counts.first()
            } else {
                counts.count().toLong()
            }
        }
    }

    private fun <T : Any> createSlice(
        query: JpqlQuery<*>,
        returnType: KClass<T>,
        pageable: Pageable,
    ): Slice<T?> {
        val enhancedQuery = createJpaEnhancedQuery(query, returnType, pageable.sort)

        val sortedQuery = enhancedQuery.sortedQuery

        return if (pageable.isPaged) {
            sortedQuery.firstResult = pageable.offset.toInt()
            sortedQuery.maxResults = pageable.pageSize + 1

            val results = sortedQuery.resultList
            val hasNext = results.size > pageable.pageSize

            SliceImpl(takeIf { hasNext }?.let { results.dropLast(1) } ?: results, pageable, hasNext)
        } else {
            val results = sortedQuery.resultList

            SliceImpl(results, pageable, false)
        }
    }
}
