package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

@SinceJdsl("3.0.0")
interface CaseValueWhenStep<T : Any, V : Any> : CaseValueElseStep<T, V>, Expressionable<V> {
    /**
     * Creates a when operator in a case expression.
     */
    @SinceJdsl("3.0.0")
    fun <S : T?> `when`(compareValue: S): CaseValueThenStep<T, V>

    /**
     * Creates a when operator in a case expression.
     */
    @SinceJdsl("3.0.0")
    fun `when`(compareValue: Expressionable<T>): CaseValueThenStep<T, V>
}
