package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.StringCastStep
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCast

@PublishedApi
internal data class JpqlStringCastStep<T : Any>(
    private val expression: Expression<T>,
) : StringCastStep {
    override fun asString(): Expressionable<String> {
        return JpqlCast(expression, String::class)
    }
}
