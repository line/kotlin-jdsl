package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable

interface CaseWhenStep<T : Any> : CaseElseStep<T>, Expressionable<T> {
    fun `when`(predicate: Predicatable): CaseThenStep<T>
}
