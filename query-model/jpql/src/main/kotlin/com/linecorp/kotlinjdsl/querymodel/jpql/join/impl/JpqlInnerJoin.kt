package com.linecorp.kotlinjdsl.querymodel.jpql.join.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@Internal
data class JpqlInnerJoin<T : Any> internal constructor(
    val entity: Entity<T>,
    val on: Predicate,
) : Join {
    override val joinType: JoinType = JoinType.INNER
    override val fetch: Boolean = false
}
