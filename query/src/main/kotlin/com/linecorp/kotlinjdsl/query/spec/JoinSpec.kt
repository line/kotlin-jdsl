package com.linecorp.kotlinjdsl.query.spec

import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import javax.persistence.criteria.JoinType

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

data class AssociateOnlyJoinSpec<L, R>(
    override val left: EntitySpec<L>,
    override val right: EntitySpec<R>,
    override val path: String
) : AssociatedJoinSpec<L, R> {
    override val joinType: JoinType = JoinType.INNER
}

data class CrossJoinSpec<T>(
    override val entity: EntitySpec<T>,
) : JoinSpec<T>
