package com.linecorp.kotlinjdsl.dsl.jpql.sort.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts

internal data class SortBuilder(
    val expr: Expression<*>,
    val order: Sort.Order,
    var nullOrder: Sort.NullOrder? = null,
) {
    fun nullsFirst(): SortBuilder {
        nullOrder = Sort.NullOrder.FIRST

        return this
    }

    fun nullsLast(): SortBuilder {
        nullOrder = Sort.NullOrder.LAST

        return this
    }

    fun build(): Sort {
        return when (order) {
            Sort.Order.ASC -> Sorts.asc(expr, nullOrder)
            Sort.Order.DESC -> Sorts.desc(expr, nullOrder)
        }
    }
}
