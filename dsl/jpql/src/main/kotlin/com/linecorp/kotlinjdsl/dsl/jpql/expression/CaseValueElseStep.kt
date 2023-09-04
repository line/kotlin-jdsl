package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

@SinceJdsl("3.0.0")
interface CaseValueElseStep<T : Any, V : Any> : Expressionable<V> {
    /**
     * Creates an else operator in a case expression.
     */
    @SinceJdsl("3.0.0")
    fun <S : V?> `else`(value: S): Expressionable<V>

    /**
     * Creates an else operator in a case expression.
     */
    @SinceJdsl("3.0.0")
    fun `else`(value: Expressionable<V>): Expressionable<V>
}
