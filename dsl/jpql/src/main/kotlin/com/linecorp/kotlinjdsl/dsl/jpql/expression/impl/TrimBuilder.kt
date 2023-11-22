package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions

internal data class TrimBuilder(
    private val character: Expression<Char>? = null,
) {
    private lateinit var value: Expression<String>

    fun from(value: Expression<String>): TrimBuilder {
        this.value = value

        return this
    }

    fun build(): Expression<String> {
        return Expressions.trim(
            character = character,
            value = value,
        )
    }
}
