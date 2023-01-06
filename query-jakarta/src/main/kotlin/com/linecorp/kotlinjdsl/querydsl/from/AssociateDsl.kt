package com.linecorp.kotlinjdsl.querydsl.from

import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import jakarta.persistence.criteria.JoinType
import kotlin.reflect.KClass

interface AssociateDsl {
    fun <T, R> associate(
        left: EntitySpec<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    )

    fun <T : Any, R> associate(
        left: KClass<T>,
        right: EntitySpec<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    ) = associate(EntitySpec(left.java), right, relation, joinType)

    fun <T, R : Any> associate(
        left: EntitySpec<T>,
        right: KClass<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    ) = associate(left, EntitySpec(right.java), relation, joinType)

    fun <T : Any, R : Any> associate(
        left: KClass<T>,
        right: KClass<R>,
        relation: Relation<T, R?>,
        joinType: JoinType = JoinType.INNER
    ) = associate(EntitySpec(left.java), EntitySpec(right.java), relation, joinType)
}
