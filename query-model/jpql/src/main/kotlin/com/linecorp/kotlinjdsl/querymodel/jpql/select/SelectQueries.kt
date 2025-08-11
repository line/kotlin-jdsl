package com.linecorp.kotlinjdsl.querymodel.jpql.select

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
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
