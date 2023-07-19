package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Expression
import kotlin.reflect.KClass

@Internal
data class TemplateExpression<T : Any>(
    val type: KClass<T>,
    val template: String,
    val args: Iterable<Expression<*>>,
) : Expression<T>
