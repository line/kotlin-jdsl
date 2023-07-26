package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

interface CaseThenStep<T : Any> {
    fun <S : T?> then(value: S): CaseWhenStep<T>

    fun then(value: Expressionable<T>): CaseWhenStep<T>
}
