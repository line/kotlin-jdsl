package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import kotlin.reflect.KClass

data class Type<T>(
    val path: Path<T>,
) : Expression<KClass<T & Any>>
