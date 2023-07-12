package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import kotlin.reflect.KClass

data class Field<T>(
    override val type: KClass<*>,
    val owner: Path<*>,
    val path: String,
) : Path<T>
