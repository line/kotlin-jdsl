package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions

internal data class TrimTrailingBuilder(
    private val character: Expression<Char>? = null,
) {
    private lateinit var value: Expression<String>

    fun from(value: Expression<String>): TrimTrailingBuilder {
        this.value = value

        return this
    }

    fun build(): Expression<String> {
        return Expressions.trimTrailing(
            character = character,
            value = value,
        )
    }
}
