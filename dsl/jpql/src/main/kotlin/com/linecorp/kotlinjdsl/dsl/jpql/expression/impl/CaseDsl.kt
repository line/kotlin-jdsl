package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDslSupport
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseElseStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseWhenStep
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate

internal class CaseDsl<T> private constructor(
    private val builder: CaseBuilder<T>,
) : CaseWhenStep<T>,
    CaseElseStep<T> {

    constructor(predicate: Predicate, then: Expression<T>) : this(CaseBuilder<T>(predicate, then))

    override fun `when`(predicate: Predicatable, then: T): CaseWhenStep<T?> {
        builder.`when`(predicate.toPredicate(), JpqlDslSupport.value(then))

        @Suppress("UNCHECKED_CAST")
        return this as CaseWhenStep<T?>
    }

    override fun `when`(predicate: Predicatable, then: Expressionable<T>): CaseWhenStep<T?> {
        builder.`when`(predicate.toPredicate(), then.toExpression())

        @Suppress("UNCHECKED_CAST")
        return this as CaseWhenStep<T?>
    }

    override fun <R : T> `else`(value: R): Expressionable<R> {
        builder.`else`(JpqlDslSupport.value(value))

        @Suppress("UNCHECKED_CAST")
        return this as Expressionable<R>
    }

    override fun <R : T> `else`(value: Expressionable<R>): Expressionable<R> {
        builder.`else`(value.toExpression())

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
