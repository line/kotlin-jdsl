package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path

internal data class CaseValueBuilder<T : Any, V : Any>(
    private val value: Path<T>,
    private val whens: MutableMap<Expression<T>, Expression<V>>,
    private var `else`: Expression<V>? = null,

    private var currentCompareValue: Expression<T>,
) {
    constructor(
        value: Path<T>,
        compareValue: Expression<T>,
        then: Expression<V>,
    ) : this(
        value,
        mutableMapOf(compareValue to then),
        null,
        compareValue,
    )

    fun `when`(value: Expression<T>): CaseValueBuilder<T, V> {
        currentCompareValue = value

        return this
    }

    fun then(value: Expression<V>): CaseValueBuilder<T, V> {
        whens[currentCompareValue] = value

        return this
    }

    fun `else`(value: Expression<V>): CaseValueBuilder<T, V> {
        this.`else` = value

        return this
    }

    fun build(): Expression<V> {
        return Expressions.caseValue(
            value,
            whens,
            `else`,
        )
    }
}
