package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

@SinceJdsl("3.0.0")
interface CaseThenStep<T : Any> {
    /**
     * Creates a then operator in a case expression.
     */
    @SinceJdsl("3.0.0")
    fun <S : T?> then(value: S): CaseWhenStep<T>

    /**
     * Creates a then operator in a case expression.
     */
    @SinceJdsl("3.0.0")
    fun then(value: Expressionable<T>): CaseWhenStep<T>
}
