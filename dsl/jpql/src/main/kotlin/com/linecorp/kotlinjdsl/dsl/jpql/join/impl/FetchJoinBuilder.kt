package com.linecorp.kotlinjdsl.dsl.jpql.join.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

internal data class FetchJoinBuilder<T : Any>(
    private var entity: Entity<T>,
    private var joinType: JoinType,
    private var predicate: Predicate? = null,
) {
    fun on(predicate: Predicate): FetchJoinBuilder<T> {
        this.predicate = predicate

        return this
    }

    fun alias(entity: Entity<T>): FetchJoinBuilder<T> {
        this.entity = entity

        return this
    }

    fun build(): Join {
        return when (joinType) {
            JoinType.INNER -> Joins.innerFetchJoin(entity, predicate!!)
            JoinType.LEFT -> Joins.leftFetchJoin(entity, predicate!!)
        }
    }
}
