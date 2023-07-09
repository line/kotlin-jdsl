package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Case
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.CaseWhen

internal class CaseBuilder<T> private constructor(
    private val whens: MutableList<CaseWhen<T>>,
) {
    constructor(
        predicate: Predicate,
        then: Expression<T>,
    ) : this(
        mutableListOf(CaseWhen(predicate, then)),
    )

    private var `else`: Expression<out T>? = null

    fun `when`(predicate: Predicate, then: Expression<T>): CaseBuilder<T> {
        whens.add(CaseWhen(predicate, then))

        return this
    }

    fun `else`(value: Expression<out T>): CaseBuilder<T> {
        this.`else` = value

        return this
    }

    fun build(): Case<T> {
        return Case(
            whens = whens,
            `else` = `else`,
        )
    }
}
