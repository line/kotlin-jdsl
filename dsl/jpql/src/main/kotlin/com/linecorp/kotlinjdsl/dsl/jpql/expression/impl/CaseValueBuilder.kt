package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

internal class CaseValueBuilder<T, V> private constructor(
    private val value: Expression<T>,
    private val whens: MutableList<Pair<Expressionable<T>, Expressionable<V>>>,
) {
    constructor(
        value: Expression<T>,
        compareValue: Expression<T>,
        then: Expression<V>,
    ) : this(
        value,
        mutableListOf(Pair(compareValue, then)),
    )

    private var `else`: Expression<out V>? = null

    fun `when`(value: Expression<T>, then: Expression<V>): CaseValueBuilder<T, V> {
        whens.add(Pair(value, then))

        return this
    }

    fun `else`(value: Expression<out V>): CaseValueBuilder<T, V> {
        this.`else` = value

        return this
    }

    fun build(): Expression<V> {
        val `else` = `else`

        return if (`else` != null) {
            Expressions.caseValue(
                value,
                whens,
                `else`,
            )
        } else {
            @Suppress("UNCHECKED_CAST")
            Expressions.caseValue(
                value,
                whens,
            ) as Expression<V>
        }
    }
}
