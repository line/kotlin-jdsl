package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.PathAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Path

data class PathAndExpressionImpl<T>(
    override val first: Path<T>,
    override val second: Expression<T>,
) : PathAndExpression<T>
