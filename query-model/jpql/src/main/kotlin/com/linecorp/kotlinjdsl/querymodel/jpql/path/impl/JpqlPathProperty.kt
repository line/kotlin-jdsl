package com.linecorp.kotlinjdsl.querymodel.jpql.path.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import kotlin.reflect.KProperty1

@Internal
data class JpqlPathProperty<T : Any, V> internal constructor(
    val path: Path<T>,
    val property: KProperty1<in T, V>,
) : Path<V & Any>
