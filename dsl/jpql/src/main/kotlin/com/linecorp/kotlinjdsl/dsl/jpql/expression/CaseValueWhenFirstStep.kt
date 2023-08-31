package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

@SinceJdsl("3.0.0")
interface CaseValueWhenFirstStep<T : Any> {
    @SinceJdsl("3.0.0")
    fun <S : T?> `when`(compareValue: S): CaseValueThenFirstStep<T>

    @SinceJdsl("3.0.0")
    fun `when`(compareValue: Expressionable<T>): CaseValueThenFirstStep<T>
}
