package com.linecorp.kotlinjdsl.querymodel.jpql.path.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import kotlin.reflect.KCallable

@Internal
data class JpqlEntityProperty<T : Any, V> internal constructor(
    val entity: Entity<T>,
    val property: KCallable<V>,
) : Path<V & Any>
