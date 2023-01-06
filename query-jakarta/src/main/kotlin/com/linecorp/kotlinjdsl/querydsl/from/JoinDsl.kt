package com.linecorp.kotlinjdsl.querydsl.from

import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import jakarta.persistence.criteria.JoinType
import kotlin.reflect.KClass

interface JoinDsl : AssociateDsl, TreatDsl {
    fun on(predicate: PredicateSpec): PredicateSpec = predicate
    fun on(predicate: () -> PredicateSpec): PredicateSpec = on(predicate())

    fun <T, R> join(
        left: EntitySpec<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    )

    fun <T : Any, R> join(
        left: KClass<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    ) = join(EntitySpec(left.java), right, relation, joinType)

    fun <T, R : Any> join(
        left: EntitySpec<T>,
        right: KClass<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    ) = join(left, EntitySpec(right.java), relation, joinType)

    fun <T : Any, R : Any> join(
        left: KClass<T>,
        right: KClass<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    ) = join(EntitySpec(left.java), EntitySpec(right.java), relation, joinType)

    fun <T> join(entity: EntitySpec<T>, predicate: PredicateSpec)
    fun <T : Any> join(entity: KClass<T>, predicate: PredicateSpec) = join(EntitySpec(entity.java), predicate)
}
