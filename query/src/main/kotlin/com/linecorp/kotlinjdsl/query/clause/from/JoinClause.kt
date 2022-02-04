package com.linecorp.kotlinjdsl.query.clause.from

import com.linecorp.kotlinjdsl.query.spec.JoinSpec

data class JoinClause(
    val joins: List<JoinSpec<*>>,
)
