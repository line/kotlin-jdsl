package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

interface CaseThenFirstStep {
    fun <T> then(value: T): CaseWhenStep<T & Any>

    fun <T : Any> then(value: Expressionable<T>): CaseWhenStep<T>
}
