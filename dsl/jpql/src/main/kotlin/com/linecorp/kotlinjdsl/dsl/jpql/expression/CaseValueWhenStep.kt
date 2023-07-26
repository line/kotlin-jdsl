package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

interface CaseValueWhenStep<T : Any, V : Any> : CaseValueElseStep<T, V>, Expressionable<V> {
    fun <S : T?> `when`(compareValue: S): CaseValueThenStep<T, V>

    fun `when`(compareValue: Expressionable<T>): CaseValueThenStep<T, V>
}
