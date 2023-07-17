package com.linecorp.kotlinjdsl.querymodel.jpql.path.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.path.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import kotlin.reflect.KClass

data class JpqlJoin internal constructor(
    val left: Path<*>,
    val right: Path<*>,
    val on: Predicate?,
    val joinType: JoinType,
    val fetch: Boolean,
) : Path<Any> {
    override val type: KClass<*> = Any::class
}
