package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression

interface ExpressionAndExpression<T> {
    val first: Expression<T>
    val second: Expression<T>
}
