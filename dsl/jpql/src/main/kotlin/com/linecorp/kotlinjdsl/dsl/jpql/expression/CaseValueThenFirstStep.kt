package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

interface CaseValueThenFirstStep<T : Any> {
    fun <V> then(value: V): CaseValueWhenStep<T, V & Any>

    fun <V : Any> then(value: Expressionable<V>): CaseValueWhenStep<T, V>
}
