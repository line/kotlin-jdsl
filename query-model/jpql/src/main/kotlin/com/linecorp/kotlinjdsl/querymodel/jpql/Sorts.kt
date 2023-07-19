package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.impl.JpqlSort

object Sorts {
    @SinceJdsl("3.0.0")
    fun asc(expr: Expressionable<*>): Sort {
        return JpqlSort(expr.toExpression(), Sort.Order.ASC)
    }

    @SinceJdsl("3.0.0")
    fun desc(expr: Expressionable<*>): Sort {
        return JpqlSort(expr.toExpression(), Sort.Order.DESC)
    }
}
