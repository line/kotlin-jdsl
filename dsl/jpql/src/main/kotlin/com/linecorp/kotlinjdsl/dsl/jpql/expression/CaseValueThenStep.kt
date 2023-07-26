package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

interface CaseValueThenStep<T : Any, V : Any> {
    fun <S : V?> then(value: S): CaseValueWhenStep<T, V>

    fun then(value: Expressionable<V>): CaseValueWhenStep<T, V>
}
