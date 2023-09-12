package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseElseStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseThenStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseWhenStep
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@PublishedApi
internal data class CaseDsl<T : Any>(
    private val builder: CaseBuilder<T>,
) : CaseWhenStep<T>,
    CaseThenStep<T>,
    CaseElseStep<T> {

    constructor(predicate: Predicate, then: Expression<T>) : this(CaseBuilder<T>(predicate, then))

    override fun `when`(predicate: Predicatable): CaseThenStep<T> {
        builder.`when`(predicate.toPredicate())

        return this
    }

    override fun <S : T?> then(value: S): CaseWhenStep<T> {
        builder.then(Expressions.value(value))

        return this
    }

    override fun then(value: Expressionable<T>): CaseWhenStep<T> {
        builder.then(value.toExpression())

        return this
    }

    override fun <S : T?> `else`(value: S): Expressionable<T> {
        builder.`else`(Expressions.value(value))

        return this
    }

    override fun `else`(value: Expressionable<T>): Expressionable<T> {
        builder.`else`(value.toExpression())

        return this
    }

    override fun toExpression(): Expression<T> {
        return builder.build()
    }
}
