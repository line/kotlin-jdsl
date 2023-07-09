package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.*
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.JpqlSelectQuery

class SelectQueryBuilder(
    private val select: Collection<Expression<*>>,
    private val distinct: Boolean,
    private var from: Collection<Path<*>>,
) {
    private var where: Predicate? = null
    private var groupBy: MutableCollection<Expression<*>>? = null
    private var having: Predicate? = null
    private var orderBy: MutableCollection<Sort>? = null

    fun where(predicate: Predicate): SelectQueryBuilder {
        where = predicate

        return this
    }

    fun groupBy(expressions: Collection<Expression<*>>): SelectQueryBuilder {
        this.groupBy = (this.groupBy ?: mutableListOf()).also { it.addAll(expressions) }

        return this
    }

    fun having(predicate: Predicate): SelectQueryBuilder {
        this.having = predicate

        return this
    }

    fun orderBy(sorts: Collection<Sort>): SelectQueryBuilder {
        this.orderBy = (this.orderBy ?: mutableListOf()).also { it.addAll(sorts) }

        return this
    }

    fun build(): SelectQuery {
        return JpqlSelectQuery(
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
