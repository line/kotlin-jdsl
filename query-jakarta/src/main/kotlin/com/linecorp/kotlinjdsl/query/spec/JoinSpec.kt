package com.linecorp.kotlinjdsl.query.spec

import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import jakarta.persistence.criteria.JoinType

sealed interface JoinSpec<T> {
    val entity: EntitySpec<T>
}

sealed interface AssociatedJoinSpec<L, R> : JoinSpec<R> {
    val left: EntitySpec<L>
    val right: EntitySpec<R>
    val path: String
    val joinType: JoinType

    override val entity get() = right
}

data class SimpleAssociatedJoinSpec<L, R>(
    override val left: EntitySpec<L>,
    override val right: EntitySpec<R>,
    override val path: String
) : AssociatedJoinSpec<L, R> {
    override val joinType: JoinType = JoinType.INNER
}

data class SimpleJoinSpec<L, R>(
    override val left: EntitySpec<L>,
    override val right: EntitySpec<R>,
    override val path: String,
    override val joinType: JoinType
) : AssociatedJoinSpec<L, R>

data class FetchJoinSpec<L, R>(
    override val left: EntitySpec<L>,
    override val right: EntitySpec<R>,
    override val path: String,
    override val joinType: JoinType
) : AssociatedJoinSpec<L, R>

data class CrossJoinSpec<T>(
    override val entity: EntitySpec<T>,
) : JoinSpec<T>

data class TreatJoinSpec<P, T : P>(
    override val left: EntitySpec<P>,
    override val right: EntitySpec<T>,
    override val joinType: JoinType,
    val root: ColumnSpec<*>
) : AssociatedJoinSpec<P, T> {
    override val path: String = root.path
}
