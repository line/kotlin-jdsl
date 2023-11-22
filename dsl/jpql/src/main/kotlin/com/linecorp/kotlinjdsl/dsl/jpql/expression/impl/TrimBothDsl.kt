package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.TrimFromStep
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions

@PublishedApi
internal data class TrimBothDsl(
    private val builder: TrimBothBuilder,
) : TrimFromStep, Expressionable<String> {
    constructor(
        character: Expression<Char>? = null,
    ) : this(TrimBothBuilder(character))

    override fun from(value: String): Expressionable<String> {
        builder.from(Expressions.value(value))

        return this
    }

    override fun from(value: Expressionable<String>): Expressionable<String> {
        builder.from(value.toExpression())

        return this
    }

    override fun toExpression(): Expression<String> {
        return builder.build()
    }
}
