package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Path

interface PathAndExpression<T> : ExpressionAndExpression<T> {
    override val first: Path<T>
    override val second: Expression<T>
}
