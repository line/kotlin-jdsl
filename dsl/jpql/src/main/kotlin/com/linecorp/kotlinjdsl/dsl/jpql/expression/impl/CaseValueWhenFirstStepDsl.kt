package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDslSupport
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenMoreStep
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressionable

internal class CaseValueWhenFirstStepDsl<T>(
    private val value: Expression<T>
) : CaseValueWhenFirstStep<T> {

    override fun <V> `when`(compareValue: T, then: V): CaseValueWhenMoreStep<T, V?> {
        return CaseValueDsl(value, JpqlDslSupport.value(compareValue), JpqlDslSupport.value(then))
    }

    override fun <V> `when`(compareValue: T, then: Expressionable<out V>): CaseValueWhenMoreStep<T, V?> {
        @Suppress("UNCHECKED_CAST")
        return CaseValueDsl(
            this.value,
            JpqlDslSupport.value(compareValue),
            then.toExpression(),
        ) as CaseValueWhenMoreStep<T, V?>
    }

    override fun <V> `when`(compareValue: Expressionable<T>, then: V): CaseValueWhenMoreStep<T, V?> {
        return CaseValueDsl(this.value, compareValue.toExpression(), JpqlDslSupport.value(then))
    }

    override fun <V> `when`(
        compareValue: Expressionable<T>,
        then: Expressionable<out V>
    ): CaseValueWhenMoreStep<T, V?> {
        @Suppress("UNCHECKED_CAST")
        return CaseValueDsl(
            this.value,
            compareValue.toExpression(),
            then.toExpression(),
        ) as CaseValueWhenMoreStep<T, V?>
    }
}
