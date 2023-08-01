package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

internal data class CaseBuilder<T : Any>(
    private val whens: MutableMap<Predicate, Expression<T>>,
    private var `else`: Expression<T>? = null,

    private var currentPredicate: Predicate,
) {
    constructor(
        predicate: Predicate,
        then: Expression<T>,
    ) : this(
        mutableMapOf(predicate to then),
        null,
        predicate,
    )

    fun `when`(predicate: Predicate): CaseBuilder<T> {
        currentPredicate = predicate

        return this
    }

    fun then(value: Expression<T>): CaseBuilder<T> {
        whens[currentPredicate] = value

        return this
    }

    fun `else`(value: Expression<T>): CaseBuilder<T> {
        `else` = value

        return this
    }

    fun build(): Expression<T> {
        return Expressions.case(
            whens = whens,
            `else` = `else`,
        )
    }
}
