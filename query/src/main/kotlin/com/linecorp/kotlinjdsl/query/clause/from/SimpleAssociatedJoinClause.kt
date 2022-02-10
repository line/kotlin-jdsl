package com.linecorp.kotlinjdsl.query.clause.from

import com.linecorp.kotlinjdsl.query.spec.SimpleAssociatedJoinSpec

@JvmInline
value class SimpleAssociatedJoinClause(val joins: List<SimpleAssociatedJoinSpec<*, *>>)
