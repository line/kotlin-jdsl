package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueElseStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueThenStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenStep
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path

internal data class CaseValueDsl<T : Any, V : Any>(
    private val builder: CaseValueBuilder<T, V>,
) : CaseValueWhenStep<T, V>,
    CaseValueThenStep<T, V>,
    CaseValueElseStep<T, V> {

    internal constructor(
        value: Path<T>,
        compareValue: Expression<T>,
        then: Expression<V>,
    ) : this(
        CaseValueBuilder<T, V>(value, compareValue, then),
    )

    override fun <S : T?> `when`(compareValue: S): CaseValueThenStep<T, V> {
        builder.`when`(Expressions.value(compareValue))

        return this
    }

    override fun `when`(compareValue: Expressionable<T>): CaseValueThenStep<T, V> {
        builder.`when`(compareValue.toExpression())

        return this
    }

    override fun <S : V?> then(value: S): CaseValueWhenStep<T, V> {
        builder.then(Expressions.value(value))

        return this
    }

    override fun then(value: Expressionable<V>): CaseValueWhenStep<T, V> {
        builder.then(value.toExpression())

        return this
    }

    override fun <S : V?> `else`(value: S): Expressionable<V> {
        builder.`else`(Expressions.value(value))

        return this
    }

    override fun `else`(value: Expressionable<V>): Expressionable<V> {
        builder.`else`(value.toExpression())

        return this
    }

    override fun toExpression(): Expression<V> {
        return builder.build()
    }
}
