package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.6.0")
enum class SetOperatorType {
    UNION,
    UNION_ALL,
    INTERSECT,
    EXCEPT,
}
