package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

interface CaseValueElseStep<T : Any, V : Any> : Expressionable<V> {
    fun <S : V?> `else`(value: S): Expressionable<V>

    fun `else`(value: Expressionable<V>): Expressionable<V>
}
