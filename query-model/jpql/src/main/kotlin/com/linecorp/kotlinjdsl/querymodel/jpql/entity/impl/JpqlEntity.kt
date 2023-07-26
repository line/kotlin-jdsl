package com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import kotlin.reflect.KClass

@Internal
data class JpqlEntity<T : Any> internal constructor(
    val type: KClass<T>,
    override val alias: String,
) : Entity<T>
