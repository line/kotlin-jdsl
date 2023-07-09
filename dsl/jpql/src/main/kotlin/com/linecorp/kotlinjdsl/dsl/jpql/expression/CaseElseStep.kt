package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.Expressionable

interface CaseElseStep<T> : Expressionable<T> {
    fun <R : T> `else`(value: R): Expressionable<R>

    fun <R : T> `else`(value: Expressionable<R>): Expressionable<R>
}
