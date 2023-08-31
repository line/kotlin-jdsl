package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable

@SinceJdsl("3.0.0")
interface CaseWhenStep<T : Any> : CaseElseStep<T>, Expressionable<T> {
    @SinceJdsl("3.0.0")
    fun `when`(predicate: Predicatable): CaseThenStep<T>
}
