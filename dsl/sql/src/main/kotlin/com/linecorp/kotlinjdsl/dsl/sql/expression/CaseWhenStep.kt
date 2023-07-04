package com.linecorp.kotlinjdsl.dsl.sql.expression

import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate

interface CaseWhenStep<T> {
    fun `when`(predicate: Predicate, then: T): CaseWhenMoreStep<T?>
    fun `when`(predicate: Predicate, then: Expression<T>): CaseWhenMoreStep<T?>
}
