package com.linecorp.kotlinjdsl.query.spec

import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import javax.persistence.criteria.JoinType

sealed class JoinSpec<T> {
    abstract val entity: EntitySpec<T>
}

sealed class AssociatedJoinSpec<L, R> : JoinSpec<R>() {
    abstract val left: EntitySpec<L>
    abstract val right: EntitySpec<R>
    abstract val path: String
    abstract val joinType: JoinType

    override val entity get() = right
}

data class SimpleJoinSpec<L, R>(
    override val left: EntitySpec<L>,
    override val right: EntitySpec<R>,
    override val path: String,
    override val joinType: JoinType
) : AssociatedJoinSpec<L, R>()

data class FetchJoinSpec<L, R>(
    override val left: EntitySpec<L>,
    override val right: EntitySpec<R>,
    override val path: String,
    override val joinType: JoinType
) : AssociatedJoinSpec<L, R>()

data class CrossJoinSpec<T>(
    override val entity: EntitySpec<T>,
) : JoinSpec<T>()