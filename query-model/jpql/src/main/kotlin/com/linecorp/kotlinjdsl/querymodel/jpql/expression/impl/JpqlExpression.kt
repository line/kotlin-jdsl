package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import kotlin.reflect.KClass

/**
 * Expression that references an expression.
 *
 * It can be used to alias an expression in a Select clause,
 * and it can be used to reference the aliased expression.
 */
@Internal
data class JpqlExpression<T : Any> internal constructor(
    val type: KClass<T>,
    val alias: String,
) : Expression<T>
