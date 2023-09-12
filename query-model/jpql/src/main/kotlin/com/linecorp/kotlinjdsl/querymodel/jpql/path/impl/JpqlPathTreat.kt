package com.linecorp.kotlinjdsl.querymodel.jpql.path.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import kotlin.reflect.KClass

@Internal
data class JpqlPathTreat<T : Any, S : T> internal constructor(
    val path: Path<T>,
    val type: KClass<S>,
) : Path<S>
