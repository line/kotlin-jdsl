package com.linecorp.kotlinjdsl.querymodel.jpql.expression

interface ExpressionAndExpression<T> {
    val first: Expression<T>
    val second: Expression<T>
}
