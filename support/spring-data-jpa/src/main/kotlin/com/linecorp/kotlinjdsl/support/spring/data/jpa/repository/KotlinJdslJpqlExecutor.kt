package com.linecorp.kotlinjdsl.support.spring.data.jpa.repository

import com.linecorp.kotlinjdsl.SinceJdsl
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
@SinceJdsl("3.0.0")
interface KotlinJdslJpqlExecutor {
    /**
     * Returns the first result of the select query.
     *
     * @throws org.springframework.dao.EmptyResultDataAccessException if there is no result
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> findFirst(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): T

    /**
     * Returns the first result of the select query.
     *
     * @throws org.springframework.dao.EmptyResultDataAccessException if there is no result
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> findFirst(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): T

    /**
     * Returns the first N results of the select query.
     *
     * @throws org.springframework.dao.EmptyResultDataAccessException if there is no result
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> findFirstN(
        n: Int,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T>

    /**
     * Returns the first N results of the select query.
     *
     * @throws org.springframework.dao.EmptyResultDataAccessException if there is no result
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> findFirstN(
        dsl: JpqlDsl.Constructor<DSL>,
        n: Int,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T>

    /**
     * Returns the first result of the select query.
     * Returns null if there is no result.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> findFirstOrNull(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): T?

    /**
     * Returns the first result of the select query.
     * Returns null if there is no result.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> findFirstOrNull(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): T?

    /**
     * Returns a single result of the select query.
     *
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException if more than one result
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> findOne(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): T?

    /**
     * Returns a single result of the select query.
     *
     * @throws org.springframework.dao.IncorrectResultSizeDataAccessException if more than one result
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> findOne(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): T?

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> findAll(
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> findAll(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T?>

    /**
     * Execute the update query.
     *
     * @return the number of entities updated
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> update(
        init: Jpql.() -> JpqlQueryable<UpdateQuery<T>>,
    ): Int

    /**
     * Execute the update query.
     *
     * @return the number of entities updated
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> update(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<UpdateQuery<T>>,
    ): Int

    /**
     * Execute the delete query.
     *
     * @return the number of entities deleted
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> delete(
        init: Jpql.() -> JpqlQueryable<DeleteQuery<T>>,
    ): Int

    /**
     * Execute the delete query.
     *
     * @return the number of entities deleted
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> delete(
        dsl: JpqlDsl.Constructor<DSL>,
        init: DSL.() -> JpqlQueryable<DeleteQuery<T>>,
    ): Int
}
