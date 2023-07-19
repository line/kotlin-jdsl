package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import kotlin.reflect.KClass

@Internal
data class JpqlField<T> internal constructor(
    override val type: KClass<*>,
    val owner: Path<*>,
    val path: String,
) : Path<T>
