package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.CaseValue
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.CaseValueWhen

internal class CaseValueBuilder<T, V> private constructor(
    private val value: Expression<T>,
    private val whens: MutableList<CaseValueWhen<T, V>>,
) {
    constructor(
        value: Expression<T>,
        compareValue: Expression<T>,
        then: Expression<V>,
    ) : this(
        value,
        mutableListOf(CaseValueWhen(compareValue, then)),
    )

    private var `else`: Expression<out V>? = null

    fun `when`(value: Expression<T>, then: Expression<V>): CaseValueBuilder<T, V> {
        whens.add(CaseValueWhen(value, then))

        return this
    }

    fun `else`(value: Expression<out V>): CaseValueBuilder<T, V> {
        this.`else` = value

        return this
    }

    fun build(): CaseValue<T, V> {
        return CaseValue(
            value = value,
            whens = whens,
            `else` = `else`,
        )
    }
}
