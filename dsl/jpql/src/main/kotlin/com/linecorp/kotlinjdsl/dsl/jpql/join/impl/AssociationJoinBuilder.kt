package com.linecorp.kotlinjdsl.dsl.jpql.join.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

internal data class AssociationJoinBuilder<T : Any>(
    private var entity: Entity<T>,
    private val association: Path<*>,
    private var joinType: JoinType,
    private var predicate: Predicate? = null,
) {
    fun on(predicate: Predicate): AssociationJoinBuilder<T> {
        this.predicate = predicate

        return this
    }

    fun alias(entity: Entity<T>): AssociationJoinBuilder<T> {
        this.entity = entity

        return this
    }

    fun build(): Join {
        return when (joinType) {
            JoinType.INNER -> Joins.innerJoin(entity, association, predicate)
            JoinType.LEFT -> Joins.leftJoin(entity, association, predicate)
        }
    }
}
