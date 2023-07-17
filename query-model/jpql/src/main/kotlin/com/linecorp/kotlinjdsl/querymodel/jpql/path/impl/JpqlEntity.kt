package com.linecorp.kotlinjdsl.querymodel.jpql.path.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import kotlin.reflect.KClass

data class JpqlEntity<T : Any> internal constructor(
    override val type: KClass<T>,
) : Path<T>
