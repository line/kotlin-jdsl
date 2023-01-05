package com.linecorp.kotlinjdsl.querydsl.from

import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import jakarta.persistence.criteria.JoinType
import kotlin.reflect.KClass

interface TreatDsl {
    fun <P, C : P> treat(
        root: ColumnSpec<*>,
        parent: EntitySpec<P>,
        child: EntitySpec<C>,
        parentJoinType: JoinType = JoinType.INNER,
    )

    fun <P : Any, C : P> treat(
        root: ColumnSpec<*>,
        parent: EntitySpec<P>,
        child: KClass<C>,
        parentJoinType: JoinType = JoinType.INNER,
    ) = treat(root, parent, EntitySpec(child.java), parentJoinType)

    fun <P : Any, C : P> treat(
        root: ColumnSpec<*>,
        parent: KClass<P>,
        child: KClass<C>,
        parentJoinType: JoinType = JoinType.INNER,
    ) = treat(root, EntitySpec(parent.java), EntitySpec(child.java), parentJoinType)
}
