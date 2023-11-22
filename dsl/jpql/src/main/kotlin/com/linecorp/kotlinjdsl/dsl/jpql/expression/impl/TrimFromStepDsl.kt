package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.TrimFromStep
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions

@PublishedApi
internal data class TrimFromStepDsl(
    private val character: Expression<Char>?,
) : TrimFromStep {
    override fun from(value: String): Expressionable<String> {
        return TrimDsl(character, Expressions.value(value))
    }

    override fun from(value: Expressionable<String>): Expressionable<String> {
        return TrimDsl(character, value.toExpression())
    }
}
