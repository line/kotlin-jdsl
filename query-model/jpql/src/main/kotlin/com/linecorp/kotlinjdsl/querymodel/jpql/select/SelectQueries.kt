package com.linecorp.kotlinjdsl.querymodel.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.from.Froms
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryExcept
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryExceptAll
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryIntersect
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryIntersectAll
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryUnion
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryUnionAll
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import kotlin.reflect.KClass

/**
 * Factory class that creates [SelectQuery].
 */
@SinceJdsl("3.0.0")
object SelectQueries {
    /**
     * Creates a select query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> selectQuery(
        returnType: KClass<T>,
        distinct: Boolean,
        select: Iterable<Expression<*>>,
        from: Iterable<From>,
        where: Predicate? = null,
        groupBy: Iterable<Expression<*>>? = null,
        having: Predicate? = null,
        orderBy: Iterable<Sort>? = null,
    ): SelectQuery<T> {
        return JpqlSelectQuery(
            returnType = returnType,
            distinct = distinct,
            select = select,
            from = Froms.reduce(from),
            where = where,
            groupBy = groupBy,
            having = having,
            orderBy = orderBy,
        )
    }

    /**
     * Returns a count query of the [query].
     *
     * If the [query] is [JpqlSelectQuery], it transforms the query into a count query as follows:
     * - The `orderBy` clause is removed.
     * - The `returnType` is changed to [Long].
     * - If `distinct` is true, it counts the first selection using `COUNT(DISTINCT ...)`.
     * - If `distinct` is false and there is only one selection, it counts that selection using `COUNT(...)`.
     * - If `distinct` is false and there are multiple selections, it counts 1 using `COUNT(1)`
     *   to ensure compatibility across different JPA implementations and more accurate results.
     *
     * If the [query] is a set operation (e.g., Union, Intersect, Except), it returns a count query
     * that counts the rows of the [query] by wrapping it in a subquery within the FROM clause
     * using the alias "derived".
     *
     * This model-based transformation ensures more accurate row counts than string-based parsing,
     * especially when dealing with complex queries including `distinct`, `groupBy`, and `having`.
     */
    @SinceJdsl("3.8.0")
    fun toCountQuery(query: SelectQuery<*>): SelectQuery<Long> {
        if (query is JpqlSelectQuery<*>) {
            return JpqlSelectQuery(
                returnType = Long::class,
                distinct = false,
                select = listOf(
                    Expressions.count(
                        distinct = query.distinct,
                        if (query.distinct) {
                            query.select.first()
                        } else if (query.select.count() == 1) {
                            query.select.first()
                        } else {
                            Expressions.intLiteral(1)
                        },
                    ),
                ),
                from = query.from,
                where = query.where,
                groupBy = query.groupBy,
                having = query.having,
                orderBy = null,
            )
        }

        val derivedEntity = Entities.derivedEntity(query, alias = "derived")

        return selectQuery(
            returnType = Long::class,
            distinct = false,
            select = listOf(Expressions.count(distinct = false, expr = Expressions.intLiteral(1))),
            from = listOf(derivedEntity),
        )
    }

    @SinceJdsl("3.6.0")
    fun <T : Any> selectUnionQuery(
        returnType: KClass<T>,
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
        orderBy: Iterable<Sort>?,
    ): SelectQuery<T> {
        return JpqlSelectQueryUnion(
            returnType = returnType,
            left = left,
            right = right,
            orderBy = orderBy,
        )
    }

    @SinceJdsl("3.6.0")
    fun <T : Any> selectUnionAllQuery(
        returnType: KClass<T>,
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
        orderBy: Iterable<Sort>?,
    ): SelectQuery<T> {
        return JpqlSelectQueryUnionAll(
            returnType = returnType,
            left = left,
            right = right,
            orderBy = orderBy,
        )
    }

    @SinceJdsl("3.6.0")
    fun <T : Any> selectExceptQuery(
        returnType: KClass<T>,
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
        orderBy: Iterable<Sort>?,
    ): SelectQuery<T> {
        return JpqlSelectQueryExcept(
            returnType = returnType,
            left = left,
            right = right,
            orderBy = orderBy,
        )
    }

    @SinceJdsl("3.6.0")
    fun <T : Any> selectExceptAllQuery(
        returnType: KClass<T>,
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
        orderBy: Iterable<Sort>?,
    ): SelectQuery<T> {
        return JpqlSelectQueryExceptAll(
            returnType = returnType,
            left = left,
            right = right,
            orderBy = orderBy,
        )
    }

    @SinceJdsl("3.6.0")
    fun <T : Any> selectIntersectQuery(
        returnType: KClass<T>,
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
        orderBy: Iterable<Sort>?,
    ): SelectQuery<T> {
        return JpqlSelectQueryIntersect(
            returnType = returnType,
            left = left,
            right = right,
            orderBy = orderBy,
        )
    }

    @SinceJdsl("3.6.0")
    fun <T : Any> selectIntersectAllQuery(
        returnType: KClass<T>,
        left: JpqlQueryable<SelectQuery<T>>,
        right: JpqlQueryable<SelectQuery<T>>,
        orderBy: Iterable<Sort>?,
    ): SelectQuery<T> {
        return JpqlSelectQueryIntersectAll(
            returnType = returnType,
            left = left,
            right = right,
            orderBy = orderBy,
        )
    }
}
