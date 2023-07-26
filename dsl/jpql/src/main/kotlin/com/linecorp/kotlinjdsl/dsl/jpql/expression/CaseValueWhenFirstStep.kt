package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

interface CaseValueWhenFirstStep<T : Any> {
    fun <S : T?> `when`(compareValue: S): CaseValueThenFirstStep<T>

    fun `when`(compareValue: Expressionable<T>): CaseValueThenFirstStep<T>
}
