package com.linecorp.kotlinjdsl.querymodel.jpql.path.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.PathAndExpression

@Internal
data class JpqlPathAndExpression<T> internal constructor(
    override val first: Path<T>,
    override val second: Expression<T>,
) : PathAndExpression<T>
