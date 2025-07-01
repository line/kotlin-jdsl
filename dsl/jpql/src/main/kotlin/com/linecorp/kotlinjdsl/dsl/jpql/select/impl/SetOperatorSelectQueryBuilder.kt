package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import kotlin.reflect.KClass

internal data class SetOperatorSelectQueryBuilder<T : Any>(
    private val returnType: KClass<T>,
    private val leftQuery: JpqlQueryable<SelectQuery<T>>,
    private val rightQuery: JpqlQueryable<SelectQuery<T>>,
    private var setOperator: SetOperator,
    private var orderBy: MutableList<Sort>? = null,
) {
    fun orderBy(sorts: Iterable<Sort>): SetOperatorSelectQueryBuilder<T> {
        this.orderBy = (this.orderBy ?: mutableListOf()).also { it.addAll(sorts) }

        return this
    }

    fun build(): SelectQuery<T> {
        return when (setOperator) {
            SetOperator.UNION -> SelectQueries.selectUnionQuery(
                returnType = returnType,
                left = leftQuery,
                right = rightQuery,
                orderBy = orderBy,
            )
            SetOperator.UNION_ALL -> SelectQueries.selectUnionAllQuery(
                returnType = returnType,
                left = leftQuery,
                right = rightQuery,
                orderBy = orderBy,
            )
        }
    }
}
