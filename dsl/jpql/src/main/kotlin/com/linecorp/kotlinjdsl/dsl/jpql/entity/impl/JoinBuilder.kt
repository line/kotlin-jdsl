package com.linecorp.kotlinjdsl.dsl.jpql.entity.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

internal data class JoinBuilder(
    private var entity: Entity<*>,
    private var association: Path<*>?,
    private var predicate: Predicate?,
    private var joinType: JoinType,
    private var fetch: Boolean,
) {
    constructor(
        entity: Entity<*>,
        association: Path<*>?,
        joinType: JoinType,
        fetch: Boolean,
    ) : this(
        entity,
        association,
        null,
        joinType,
        fetch,
    )

    fun on(predicate: Predicate): JoinBuilder {
        this.predicate = predicate

        return this
    }

    fun build(): Join {
        @Suppress("UNCHECKED_CAST")
        return Joins.join(
            entity = entity as Entity<Any>,
            association = association as? Path<Any>,
            predicate = predicate,
            joinType = joinType,
            fetch = fetch,
        )
    }
}
