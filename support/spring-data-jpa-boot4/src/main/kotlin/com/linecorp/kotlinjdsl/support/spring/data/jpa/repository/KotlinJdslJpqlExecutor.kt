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
import org.springframework.data.domain.Slice
import org.springframework.data.repository.NoRepositoryBean
import java.util.stream.Stream

@NoRepositoryBean
@SinceJdsl("3.0.0")
interface KotlinJdslJpqlExecutor {
    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> findAll(
        offset: Int? = null,
        limit: Int? = null,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: JpqlDsl.Constructor<DSL>,
        offset: Int? = null,
        limit: Int? = null,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: DSL,
        offset: Int? = null,
        limit: Int? = null,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> findAll(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, DSL : JpqlDsl> findAll(
        dsl: DSL,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): List<T?>

    /**
     * Returns the page of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> findPage(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T>

    /**
     * Returns the page of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> findPage(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T>

    /**
     * Returns the page of the select query.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, DSL : JpqlDsl> findPage(
        dsl: DSL,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Page<T>

    /**
     * Returns the slice of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> findSlice(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Slice<T>

    /**
     * Returns the slice of the select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any, DSL : JpqlDsl> findSlice(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Slice<T>

    /**
     * Returns the slice of the select query.
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, DSL : JpqlDsl> findSlice(
        dsl: DSL,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Slice<T>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.5.4")
    fun <T : Any> findStream(
        offset: Int? = null,
        limit: Int? = null,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.5.4")
    fun <T : Any, DSL : JpqlDsl> findStream(
        dsl: JpqlDsl.Constructor<DSL>,
        offset: Int? = null,
        limit: Int? = null,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.5.4")
    fun <T : Any, DSL : JpqlDsl> findStream(
        dsl: DSL,
        offset: Int? = null,
        limit: Int? = null,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.5.4")
    fun <T : Any> findStream(
        pageable: Pageable,
        init: Jpql.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.5.4")
    fun <T : Any, DSL : JpqlDsl> findStream(
        dsl: JpqlDsl.Constructor<DSL>,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T>

    /**
     * Returns all results of the select query.
     */
    @SinceJdsl("3.5.4")
    fun <T : Any, DSL : JpqlDsl> findStream(
        dsl: DSL,
        pageable: Pageable,
        init: DSL.() -> JpqlQueryable<SelectQuery<T>>,
    ): Stream<T>

    /**
     * Execute the update query.
     *
     * @return the number of entities updated
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> update(init: Jpql.() -> JpqlQueryable<UpdateQuery<T>>): Int

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
     * Execute the update query.
     *
     * @return the number of entities updated
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, DSL : JpqlDsl> update(
        dsl: DSL,
        init: DSL.() -> JpqlQueryable<UpdateQuery<T>>,
    ): Int

    /**
     * Execute the delete query.
     *
     * @return the number of entities deleted
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> delete(init: Jpql.() -> JpqlQueryable<DeleteQuery<T>>): Int

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

    /**
     * Execute the delete query.
     *
     * @return the number of entities deleted
     */
    @SinceJdsl("3.4.0")
    fun <T : Any, DSL : JpqlDsl> delete(
        dsl: DSL,
        init: DSL.() -> JpqlQueryable<DeleteQuery<T>>,
    ): Int
}
