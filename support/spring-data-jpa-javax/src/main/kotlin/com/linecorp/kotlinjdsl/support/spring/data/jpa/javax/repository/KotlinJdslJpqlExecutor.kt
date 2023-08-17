package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface KotlinJdslJpqlExecutor {
    /**
     * @throws org.springframework.dao.EmptyResultDataAccessException if there is no result
     */
    fun <T : Any> findFirst(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): T

    /**
     * @throws org.springframework.dao.EmptyResultDataAccessException if there is no result
     */
    fun <T : Any, DSL : JpqlDsl> findFirst(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): T

    /**
     * @throws org.springframework.dao.EmptyResultDataAccessException if there is no result
     */
    fun <T : Any> findFirstN(
        n: Int,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T>

    /**
     * @throws org.springframework.dao.EmptyResultDataAccessException if there is no result
     */
    fun <T : Any, DSL : JpqlDsl> findFirstN(
        dsl: JpqlDsl.Constructor<DSL>,
        n: Int,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T>

    fun <T : Any> findFirstOrNull(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): T?

    fun <T : Any, DSL : JpqlDsl> findFirstOrNull(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): T?

    /**
     * @throws javax.persistence.NonUniqueResultException if more than one result
     */
    fun <T : Any> findOne(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): T?

    /**
     * @throws javax.persistence.NonUniqueResultException if more than one result
     */
    fun <T : Any, DSL : JpqlDsl> findOne(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): T?

    fun <T : Any> findAll(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?>

    fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?>

    fun <T : Any> findAll(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?>

    fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?>

    fun <T : Any> update(
        init: Jpql.() -> JpqlQueryable<UpdateQuery<T>>,
    )

    fun <T : Any, DSL : JpqlDsl> update(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<UpdateQuery<T>>,
    )

    fun <T : Any> delete(
        init: Jpql.() -> JpqlQueryable<DeleteQuery<T>>,
    )

    fun <T : Any, DSL : JpqlDsl> delete(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<DeleteQuery<T>>,
    )
}
