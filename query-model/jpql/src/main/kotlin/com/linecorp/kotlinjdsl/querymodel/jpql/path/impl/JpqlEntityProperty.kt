package com.linecorp.kotlinjdsl.querymodel.jpql.path.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import kotlin.reflect.KProperty1

@Internal
data class JpqlEntityProperty<T : Any, V> internal constructor(
    val entity: Entity<*>,
    val property: KProperty1<T, V>,
) : Path<V & Any>
