package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

@SinceJdsl("3.0.0")
interface CaseValueThenStep<T : Any, V : Any> {
    /**
     * Creates a then operator in a case expression.
     */
    @SinceJdsl("3.0.0")
    fun <S : V?> then(value: S): CaseValueWhenStep<T, V>

    /**
     * Creates a then operator in a case expression.
     */
    @SinceJdsl("3.0.0")
    fun then(value: Expressionable<V>): CaseValueWhenStep<T, V>
}
