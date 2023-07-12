package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate
import kotlin.reflect.KClass

data class Join<T>(
    val left: Path<*>,
    val right: Path<T>,
    val on: Predicate?,
    val joinType: JoinType,
    val fetch: Boolean,
) : Path<T> {
    override val type: KClass<*> = right.type
}
