package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions

@PublishedApi
internal data class TrimBothDsl(
    private val character: Expression<Char>?,
    private val value: Expression<String>,
) : Expressionable<String> {
    override fun toExpression(): Expression<String> {
        return Expressions.trimBoth(
            character = character,
            value = value,
        )
    }
}
