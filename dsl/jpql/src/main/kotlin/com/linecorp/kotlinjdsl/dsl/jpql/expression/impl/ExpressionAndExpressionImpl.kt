package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.ExpressionAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression

data class ExpressionAndExpressionImpl<T>(
    override val first: Expression<T>,
    override val second: Expression<T>,
) : ExpressionAndExpression<T>
