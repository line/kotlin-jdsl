package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate
import kotlin.reflect.KClass

data class Join(
    val left: Path<*>,
    val right: Path<*>,
    val on: Predicate?,
    val joinType: JoinType,
    val fetch: Boolean,
) : Path<Any> {
    override val type: KClass<*> = Any::class
}
