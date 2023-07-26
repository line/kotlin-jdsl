package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueThenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenStep
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path

internal data class CaseValueThenFirstStepDsl<T : Any>(
    private val value: Path<T>,
    private val compareValue: Expression<T>,
) : CaseValueThenFirstStep<T> {
    override fun <V> then(value: V): CaseValueWhenStep<T, V & Any> {
        return CaseValueDsl(this.value, compareValue, Expressions.value(value))
    }

    override fun <V : Any> then(value: Expressionable<V>): CaseValueWhenStep<T, V> {
        return CaseValueDsl(this.value, compareValue, value.toExpression())
    }
}
