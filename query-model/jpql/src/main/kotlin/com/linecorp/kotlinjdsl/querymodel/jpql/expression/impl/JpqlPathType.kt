package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import kotlin.reflect.KClass

@Internal
data class JpqlPathType internal constructor(
    val path: Path<*>,
) : Expression<KClass<*>>
