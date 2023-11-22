package com.linecorp.kotlinjdsl.querymodel.jpql.path.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import kotlin.reflect.KCallable

@Internal
data class JpqlPathProperty<T : Any, V> internal constructor(
    val path: Path<T>,
    val property: KCallable<V>,
) : Path<V & Any>
