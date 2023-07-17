package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

internal class CaseBuilder<T> private constructor(
    private val whens: MutableList<Pair<Predicate, Expressionable<T>>>,
) {
    constructor(
        predicate: Predicate,
        then: Expression<T>,
    ) : this(
        mutableListOf(Pair(predicate, then)),
    )

    private var `else`: Expression<out T>? = null

    fun `when`(predicate: Predicate, then: Expression<T>): CaseBuilder<T> {
        whens.add(Pair(predicate, then))

        return this
    }

    fun `else`(value: Expression<out T>): CaseBuilder<T> {
        this.`else` = value

        return this
    }

    fun build(): Expression<T> {
        val `else` = this.`else`

        return if (`else` != null) {
            Expressions.case(
                whens = whens,
                `else` = `else`,
            )
        } else {
            @Suppress("UNCHECKED_CAST")
            Expressions.case(
                whens = whens,
            ) as Expression<T>
        }
    }
}
