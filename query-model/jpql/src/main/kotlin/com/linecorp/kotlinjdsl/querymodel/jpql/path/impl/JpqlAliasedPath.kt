package com.linecorp.kotlinjdsl.querymodel.jpql.path.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import kotlin.reflect.KClass

@Internal
data class JpqlAliasedPath<T> internal constructor(
    val path: Path<T>,
    val alias: String,
) : Path<T> {
    override val type: KClass<*> = path.type
}
