package com.linecorp.kotlinjdsl.querymodel.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
interface Expressionable<out T : Any> {
    fun toExpression(): Expression<T>
}
