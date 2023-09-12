package com.linecorp.kotlinjdsl.querymodel.jpql.sort

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.impl.JpqlSort

/**
 * Factory class that creates [Sort].
 */
@SinceJdsl("3.0.0")
object Sorts {
    /**
     * Creates a sort that sorts the expression in ascending order.
     */
    @SinceJdsl("3.0.0")
    fun asc(expr: Expression<*>, nullOrder: Sort.NullOrder? = null): Sort {
        return JpqlSort(expr, Sort.Order.ASC, nullOrder)
    }

    /**
     * Creates a sort that sorts the expression in descending order.
     */
    @SinceJdsl("3.0.0")
    fun desc(expr: Expression<*>, nullOrder: Sort.NullOrder? = null): Sort {
        return JpqlSort(expr, Sort.Order.DESC, nullOrder)
    }
}
