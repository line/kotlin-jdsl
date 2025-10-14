package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.6.0")
@PublishedApi
internal enum class SetOperator {
    UNION,
    UNION_ALL,
    EXCEPT,
    EXCEPT_ALL,
    INTERSECT,
    INTERSECT_ALL,
}
