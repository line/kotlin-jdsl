package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

@SinceJdsl("3.0.0")
interface CaseElseStep<T : Any> : Expressionable<T> {
    @SinceJdsl("3.0.0")
    fun <S : T?> `else`(value: S): Expressionable<T>

    @SinceJdsl("3.0.0")
    fun `else`(value: Expressionable<T>): Expressionable<T>
}
