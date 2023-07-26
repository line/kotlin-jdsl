package com.linecorp.kotlinjdsl.querymodel.jpql.join.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@Internal
data class JpqlAssociationJoin internal constructor(
    val entity: Entity<*>,
    val association: Path<*>,
    val on: Predicate?,
    val joinType: JoinType,
    val fetch: Boolean,
) : Join
