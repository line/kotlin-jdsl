package com.linecorp.kotlinjdsl.querymodel.jpql.select.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import kotlin.reflect.KClass

@Internal
@SinceJdsl("3.6.0")
data class JpqlSelectQueryIntersect<T : Any> internal constructor(
    override val returnType: KClass<T>,
    val left: JpqlQueryable<SelectQuery<T>>,
    val right: JpqlQueryable<SelectQuery<T>>,
    val orderBy: Iterable<Sort>?,
) : SelectQuery<T>
