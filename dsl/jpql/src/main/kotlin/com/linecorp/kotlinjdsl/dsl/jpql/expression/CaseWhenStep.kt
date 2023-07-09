package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate

interface CaseWhenStep<T> : CaseElseStep<T>, Expressionable<T> {
    fun `when`(predicate: Predicate, then: T): CaseWhenStep<T?>

    fun `when`(predicate: Predicate, then: Expressionable<T>): CaseWhenStep<T?>
}
