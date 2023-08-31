package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

@SinceJdsl("3.0.0")
interface CaseThenStep<T : Any> {
    @SinceJdsl("3.0.0")
    fun <S : T?> then(value: S): CaseWhenStep<T>

    @SinceJdsl("3.0.0")
    fun then(value: Expressionable<T>): CaseWhenStep<T>
}
