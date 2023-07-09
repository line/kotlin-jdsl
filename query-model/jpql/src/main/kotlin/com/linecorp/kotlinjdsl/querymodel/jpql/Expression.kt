package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.QueryPart

@SinceJdsl("3.0.0")
interface Expression<T> : Expressionable<T>, QueryPart {
    override fun toExpression(): Expression<T> = this
}
