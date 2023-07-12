package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import kotlin.reflect.KClass

data class AliasedPath<T>(
    val path: Path<T>,
    val alias: String,
) : Path<T> {
    override val type: KClass<*> = path.type
}
