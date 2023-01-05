package com.linecorp.kotlinjdsl.querydsl.from

import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import jakarta.persistence.criteria.JoinType

inline fun <reified P : Any, reified T : P> TreatDsl.treat(parentJoinType: JoinType = JoinType.INNER) {
    treat(ColumnSpec<P>(EntitySpec(P::class.java), ""), P::class, T::class, parentJoinType)
}

inline fun <reified P : Any, reified T : P> TreatDsl.treat(
    root: ColumnSpec<*>,
    parentJoinType: JoinType = JoinType.INNER
) {
    treat(root, EntitySpec(P::class.java), T::class, parentJoinType)
}
