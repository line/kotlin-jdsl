package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueThenFirstStep
import com.linecorp.kotlinjdsl.dsl.jpql.expression.CaseValueWhenFirstStep
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path

internal data class CaseValueWhenFirstStepDsl<T : Any>(
    private val value: Path<T>
) : CaseValueWhenFirstStep<T> {
    override fun <S : T?> `when`(compareValue: S): CaseValueThenFirstStep<T> {
        return CaseValueThenFirstStepDsl(value, Expressions.value(compareValue))
    }

    override fun `when`(compareValue: Expressionable<T>): CaseValueThenFirstStep<T> {
        return CaseValueThenFirstStepDsl(value, compareValue.toExpression())
    }
}
