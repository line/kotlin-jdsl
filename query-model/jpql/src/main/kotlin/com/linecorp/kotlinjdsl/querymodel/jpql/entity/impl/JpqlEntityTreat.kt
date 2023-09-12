package com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import kotlin.reflect.KClass

@Internal
data class JpqlEntityTreat<T : Any, S : T> internal constructor(
    val entity: Entity<T>,
    val type: KClass<S>,
) : Entity<S> {
    override val alias: String get() = entity.alias
}
