package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import org.springframework.beans.factory.BeanFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.NoRepositoryBean
import java.util.stream.Stream

@NoRepositoryBean
@SinceJdsl("3.6.1")
open class KotlinJdslJpqlExecutorProxy(
    private val beanFactory: BeanFactory,
) : KotlinJdslJpqlExecutor {
    private var delegate: KotlinJdslJpqlExecutor? = null

    private fun getDelegate(): KotlinJdslJpqlExecutor {
        if (delegate == null) {
            delegate = beanFactory.getBean("kotlinJdslJpqlExecutor", KotlinJdslJpqlExecutor::class.java)
        }

        return delegate!!
    }

    override fun <T : Any> findAll(
        offset: Int?,
        limit: Int?,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> = getDelegate().findAll(offset, limit, init)

    override fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: JpqlDsl.Constructor<DSL>,
        offset: Int?,
        limit: Int?,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> = getDelegate().findAll(dsl, offset, limit, init)

    override fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: DSL,
        offset: Int?,
        limit: Int?,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> = getDelegate().findAll(dsl, offset, limit, init)

    override fun <T : Any> findAll(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> = getDelegate().findAll(pageable, init)

    override fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> = getDelegate().findAll(dsl, pageable, init)

    override fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: DSL,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?> = getDelegate().findAll(dsl, pageable, init)

    override fun <T : Any> findPage(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?> = getDelegate().findPage(pageable, init)

    override fun <T : Any, DSL : JpqlDsl> findPage(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?> = getDelegate().findPage(dsl, pageable, init)

    override fun <T : Any, DSL : JpqlDsl> findPage(
        dsl: DSL,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?> = getDelegate().findPage(dsl, pageable, init)

    override fun <T : Any> findSlice(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Slice<T?> = getDelegate().findSlice(pageable, init)

    override fun <T : Any, DSL : JpqlDsl> findSlice(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Slice<T?> = getDelegate().findSlice(dsl, pageable, init)

    override fun <T : Any, DSL : JpqlDsl> findSlice(
        dsl: DSL,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Slice<T?> = getDelegate().findSlice(dsl, pageable, init)

    override fun <T : Any> findStream(
        offset: Int?,
        limit: Int?,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T?> = getDelegate().findStream(offset, limit, init)

    override fun <T : Any, DSL : JpqlDsl> findStream(
        dsl: JpqlDsl.Constructor<DSL>,
        offset: Int?,
        limit: Int?,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T?> = getDelegate().findStream(dsl, offset, limit, init)

    override fun <T : Any, DSL : JpqlDsl> findStream(
        dsl: DSL,
        offset: Int?,
        limit: Int?,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T?> = getDelegate().findStream(dsl, offset, limit, init)

    override fun <T : Any> findStream(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T?> = getDelegate().findStream(pageable, init)

    override fun <T : Any, DSL : JpqlDsl> findStream(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T?> = getDelegate().findStream(dsl, pageable, init)

    override fun <T : Any, DSL : JpqlDsl> findStream(
        dsl: DSL,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T?> = getDelegate().findStream(dsl, pageable, init)

    override fun <T : Any> update(
        init: Jpql.() -> JpqlQueryable<UpdateQuery<T>>,
    ): Int = getDelegate().update(init)

    override fun <T : Any, DSL : JpqlDsl> update(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<UpdateQuery<T>>,
    ): Int = getDelegate().update(dsl, init)

    override fun <T : Any, DSL : JpqlDsl> update(
        dsl: DSL,
        init: DSL.() -> JpqlQueryable<UpdateQuery<T>>,
    ): Int = getDelegate().update(dsl, init)

    override fun <T : Any> delete(
        init: Jpql.() -> JpqlQueryable<DeleteQuery<T>>,
    ): Int = getDelegate().delete(init)

    override fun <T : Any, DSL : JpqlDsl> delete(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<DeleteQuery<T>>,
    ): Int = getDelegate().delete(dsl, init)

    override fun <T : Any, DSL : JpqlDsl> delete(
        dsl: DSL,
        init: DSL.() -> JpqlQueryable<DeleteQuery<T>>,
    ): Int = getDelegate().delete(dsl, init)
}
