package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

@SinceJdsl("3.0.0")
interface CaseThenFirstStep {
    @SinceJdsl("3.0.0")
    fun <T> then(value: T): CaseWhenStep<T & Any>

    @SinceJdsl("3.0.0")
    fun <T : Any> then(value: Expressionable<T>): CaseWhenStep<T>
}
