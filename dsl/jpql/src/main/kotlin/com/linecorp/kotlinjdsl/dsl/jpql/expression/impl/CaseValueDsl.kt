package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDslSupport
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueElseStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenMoreStep
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressionable

internal class CaseValueDsl<T, V> private constructor(
    private val builder: CaseValueBuilder<T, V>,
) : CaseValueWhenMoreStep<T, V>,
    CaseValueElseStep<T, V> {

    internal constructor(
        value: Expression<T>,
        compareValue: Expression<T>,
        then: Expression<V>,
    ) : this(
        CaseValueBuilder<T, V>(value, compareValue, then),
    )

    override fun `when`(compareValue: T, then: V): CaseValueWhenMoreStep<T, V?> {
        builder.`when`(JpqlDslSupport.value(compareValue), JpqlDslSupport.value(then))

        @Suppress("UNCHECKED_CAST")
        return this as CaseValueWhenMoreStep<T, V?>
    }

    override fun `when`(compareValue: T, then: Expressionable<out V>): CaseValueWhenMoreStep<T, V?> {
        @Suppress("UNCHECKED_CAST")
        builder.`when`(JpqlDslSupport.value(compareValue), then.toExpression() as Expression<V>)

        @Suppress("UNCHECKED_CAST")
        return this as CaseValueWhenMoreStep<T, V?>
    }

    override fun `when`(compareValue: Expressionable<T>, then: V): CaseValueWhenMoreStep<T, V?> {
        builder.`when`(compareValue.toExpression(), JpqlDslSupport.value(then))

        @Suppress("UNCHECKED_CAST")
        return this as CaseValueWhenMoreStep<T, V?>
    }

    override fun `when`(compareValue: Expressionable<T>, then: Expressionable<out V>): CaseValueWhenMoreStep<T, V?> {
        @Suppress("UNCHECKED_CAST")
        builder.`when`(compareValue.toExpression(), then.toExpression() as Expression<V>)

        @Suppress("UNCHECKED_CAST")
        return this as CaseValueWhenMoreStep<T, V?>
    }

    override fun <R : V> `else`(value: R): Expressionable<R> {
        builder.`else`(JpqlDslSupport.value(value))

        @Suppress("UNCHECKED_CAST")
        return this as Expressionable<R>
    }

    override fun <R : V> `else`(value: Expressionable<R>): Expressionable<R> {
        builder.`else`(value.toExpression())

        @Suppress("UNCHECKED_CAST")
        return this as Expressionable<R>
    }

    override fun toExpression(): Expression<V> {
        return builder.build()
    }
}
