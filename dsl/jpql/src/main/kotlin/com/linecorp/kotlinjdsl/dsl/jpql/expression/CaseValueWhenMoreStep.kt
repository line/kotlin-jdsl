package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.Expressionable

interface CaseValueWhenMoreStep<T, V> : CaseValueElseStep<T, V>, Expressionable<V> {
    fun `when`(compareValue: T, then: V): CaseValueWhenMoreStep<T, V?>

    fun `when`(compareValue: T, then: Expressionable<out V>): CaseValueWhenMoreStep<T, V?>

    fun `when`(compareValue: Expressionable<T>, then: V): CaseValueWhenMoreStep<T, V?>

    fun `when`(compareValue: Expressionable<T>, then: Expressionable<out V>): CaseValueWhenMoreStep<T, V?>
}
