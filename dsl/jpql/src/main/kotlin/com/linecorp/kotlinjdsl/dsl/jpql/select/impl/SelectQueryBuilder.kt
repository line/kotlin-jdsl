package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import kotlin.reflect.KClass

internal data class SelectQueryBuilder<T : Any>(
    private val returnType: KClass<T>,
    private val distinct: Boolean,
    private val select: Iterable<Expression<*>>,
    private var from: Iterable<From>,
    private var where: Predicate? = null,
    private var groupBy: MutableList<Expression<*>>? = null,
    private var having: Predicate? = null,
    private var orderBy: MutableList<Sort>? = null,
) {
    fun where(predicate: Predicate): SelectQueryBuilder<T> {
        where = predicate

        return this
    }

    fun groupBy(expr: Iterable<Expression<*>>): SelectQueryBuilder<T> {
        this.groupBy = (this.groupBy ?: mutableListOf()).also { it.addAll(expr) }

        return this
    }

    fun having(predicate: Predicate): SelectQueryBuilder<T> {
        this.having = predicate

        return this
    }

    fun orderBy(sorts: Iterable<Sort>): SelectQueryBuilder<T> {
        this.orderBy = (this.orderBy ?: mutableListOf()).also { it.addAll(sorts) }

        return this
    }

    fun build(): SelectQuery<T> {
        return SelectQueries.selectQuery(
            returnType = returnType,
            select = select,
            distinct = distinct,
            from = from,
            where = where,
            groupBy = groupBy,
            having = having,
            orderBy = orderBy,
        )
    }
}
