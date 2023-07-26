package com.linecorp.kotlinjdsl.querymodel.jpql.join

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlAssociationJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

object Joins {
    @SinceJdsl("3.0.0")
    fun <T : Any> join(
        entity: Entity<T>,
        association: Path<T>?,
        predicate: Predicate?,
        joinType: JoinType,
        fetch: Boolean,
    ): Join {
        return if (association != null) {
            JpqlAssociationJoin(
                entity = entity,
                association = association,
                on = predicate,
                joinType = joinType,
                fetch = fetch,
            )
        } else {
            JpqlJoin(
                entity = entity,
                on = predicate,
                joinType = joinType,
                fetch = fetch,
            )
        }
    }
}
