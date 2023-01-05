package com.linecorp.kotlinjdsl.querydsl.from

import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import jakarta.persistence.criteria.JoinType
import kotlin.reflect.KClass

interface FetchDsl {
    fun <T, R> fetch(
        left: EntitySpec<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    )

    fun <T : Any, R> fetch(
        left: KClass<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    ) = fetch(EntitySpec(left.java), right, relation, joinType)

    fun <T, R : Any> fetch(
        left: EntitySpec<T>,
        right: KClass<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    ) = fetch(left, EntitySpec(right.java), relation, joinType)

    fun <T : Any, R : Any> fetch(
        left: KClass<T>,
        right: KClass<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    ) = fetch(EntitySpec(left.java), EntitySpec(right.java), relation, joinType)
}
