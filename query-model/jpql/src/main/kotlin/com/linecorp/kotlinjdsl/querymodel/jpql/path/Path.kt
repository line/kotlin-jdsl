package com.linecorp.kotlinjdsl.querymodel.jpql.path

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@SinceJdsl("3.0.0")
interface Path<T : Any> : Pathable<T>, Expression<T>, QueryPart {
    override fun toPath(): Path<T> = this
    override fun toExpression(): Expression<T> = this
}
