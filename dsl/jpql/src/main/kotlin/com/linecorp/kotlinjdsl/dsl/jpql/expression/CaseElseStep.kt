package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

interface CaseElseStep<T : Any> : Expressionable<T> {
    fun <S : T?> `else`(value: S): Expressionable<T>

    fun `else`(value: Expressionable<T>): Expressionable<T>
}
