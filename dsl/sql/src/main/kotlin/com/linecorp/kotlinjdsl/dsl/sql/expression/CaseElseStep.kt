package com.linecorp.kotlinjdsl.dsl.sql.expression

import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.Expressionable

interface CaseElseStep<T> : Expressionable<T> {
    fun <R : T> `else`(value: R): Expressionable<R>
    fun <R : T> `else`(expression: Expression<R>): Expressionable<R>
}
