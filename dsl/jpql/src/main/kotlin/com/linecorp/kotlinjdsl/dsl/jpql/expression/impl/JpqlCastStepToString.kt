package com.linecorp.kotlinjdsl.dsl.jpql.expression.impl

import com.linecorp.kotlinjdsl.dsl.jpql.expression.CastStepToString
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCast

@PublishedApi
internal data class JpqlCastStepToString<T : Any>(
    private val expression: Expression<T>,
) : CastStepToString {
    override fun asString(): Expressionable<String> {
        return JpqlCast(expression, String::class)
    }
}
