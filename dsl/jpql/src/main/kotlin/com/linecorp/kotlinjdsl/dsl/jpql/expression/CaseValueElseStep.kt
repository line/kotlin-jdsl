package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

interface CaseValueElseStep<T, V> : Expressionable<V> {
    fun <R : V> `else`(value: R): Expressionable<R>

    fun <R : V> `else`(value: Expressionable<R>): Expressionable<R>
}
