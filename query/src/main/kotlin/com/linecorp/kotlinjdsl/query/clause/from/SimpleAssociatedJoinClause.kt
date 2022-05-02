package com.linecorp.kotlinjdsl.query.clause.from

import com.linecorp.kotlinjdsl.query.spec.SimpleAssociatedJoinSpec

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
@JvmInline
value class SimpleAssociatedJoinClause(val joins: List<SimpleAssociatedJoinSpec<*, *>>)
