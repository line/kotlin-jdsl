package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

@SinceJdsl("3.0.0")
interface CaseValueThenFirstStep<T : Any> {
    @SinceJdsl("3.0.0")
    fun <V> then(value: V): CaseValueWhenStep<T, V & Any>

    @SinceJdsl("3.0.0")
    fun <V : Any> then(value: Expressionable<V>): CaseValueWhenStep<T, V>
}
