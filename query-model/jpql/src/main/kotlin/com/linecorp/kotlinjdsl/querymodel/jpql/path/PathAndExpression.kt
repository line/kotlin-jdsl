package com.linecorp.kotlinjdsl.querymodel.jpql.path

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.ExpressionAndExpression

interface PathAndExpression<T> : ExpressionAndExpression<T> {
    override val first: Path<T>
    override val second: Expression<T>
}
