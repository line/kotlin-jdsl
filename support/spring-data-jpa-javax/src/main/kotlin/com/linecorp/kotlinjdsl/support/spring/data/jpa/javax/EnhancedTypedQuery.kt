package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax

import javax.persistence.TypedQuery

internal class EnhancedTypedQuery<T : Any>(
    val sortedQuery: TypedQuery<T>,
    countQuery: () -> TypedQuery<Long>,
) {
    val countQuery: TypedQuery<Long> by lazy(LazyThreadSafetyMode.NONE) { countQuery() }
}
