package com.linecorp.kotlinjdsl.dsl.sql.expression.impl

import com.linecorp.kotlinjdsl.dsl.sql.expression.CaseElseStep
import com.linecorp.kotlinjdsl.dsl.sql.expression.CaseWhenFirstStep
import com.linecorp.kotlinjdsl.dsl.sql.expression.CaseWhenMoreStep
import com.linecorp.kotlinjdsl.query.sql.Expression
import com.linecorp.kotlinjdsl.query.sql.Expressionable
import com.linecorp.kotlinjdsl.query.sql.Predicate
import com.linecorp.kotlinjdsl.query.sql.impl.Literal

class CaseDsl<T> private constructor(
    private val builder: CaseBuilder<T>,
) : CaseWhenFirstStep<T>,
    CaseWhenMoreStep<T>,
    CaseElseStep<T> {

    constructor() : this(CaseBuilder())

    override fun `when`(predicate: Predicate, then: T): CaseWhenMoreStep<T?> {
        builder.`when`(predicate, Literal(then))

        @Suppress("UNCHECKED_CAST")
        return this as CaseWhenMoreStep<T?>
    }

    override fun `when`(predicate: Predicate, then: Expression<T>): CaseWhenMoreStep<T?> {
        builder.`when`(predicate, then)

        @Suppress("UNCHECKED_CAST")
        return this as CaseWhenMoreStep<T?>
    }

    override fun <R : T> `else`(value: R): Expressionable<R> {
        builder.`else`(Literal(value))

        @Suppress("UNCHECKED_CAST")
        return this as Expressionable<R>
    }

    override fun <R : T> `else`(expression: Expression<R>): Expressionable<R> {
        builder.`else`(expression)

        @Suppress("UNCHECKED_CAST")
        return this as Expressionable<R>
    }

    override fun toExpression(): Expression<T> {
        return builder.build()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CaseDsl<*>

        return builder == other.builder
    }

    override fun hashCode(): Int {
        return builder.hashCode()
    }

    override fun toString(): String {
        return "CaseDsl(" +
            "builder=$builder)"
    }
}
