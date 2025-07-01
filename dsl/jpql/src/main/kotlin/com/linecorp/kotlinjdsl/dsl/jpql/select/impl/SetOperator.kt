package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.6.0")
@Internal
enum class SetOperator {
    UNION,
    UNION_ALL,
}
