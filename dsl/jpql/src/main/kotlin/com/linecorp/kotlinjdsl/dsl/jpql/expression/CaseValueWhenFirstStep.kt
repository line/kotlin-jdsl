package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

interface CaseValueWhenFirstStep<T> {
    fun <V> `when`(compareValue: T, then: V): CaseValueWhenMoreStep<T, V?>

    fun <V> `when`(compareValue: T, then: Expressionable<out V>): CaseValueWhenMoreStep<T, V?>

    fun <V> `when`(compareValue: Expressionable<T>, then: V): CaseValueWhenMoreStep<T, V?>

    fun <V> `when`(compareValue: Expressionable<T>, then: Expressionable<out V>): CaseValueWhenMoreStep<T, V?>
}
