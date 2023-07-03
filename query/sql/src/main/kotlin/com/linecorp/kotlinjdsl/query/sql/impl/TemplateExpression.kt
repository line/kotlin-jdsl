package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Expression
import kotlin.reflect.KClass

@Internal
data class TemplateExpression<T : Any>(
    val type: KClass<T>,
    val template: String,
    val args: Collection<Expression<*>>,
) : Expression<T>
