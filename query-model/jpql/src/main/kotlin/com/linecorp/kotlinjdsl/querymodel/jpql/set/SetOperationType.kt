package com.linecorp.kotlinjdsl.querymodel.jpql.set

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.6.0")
enum class SetOperationType(val value: String) {
    UNION("UNION"),
    UNION_ALL("UNION ALL"),
    INTERSECT("INTERSECT"),
    EXCEPT("EXCEPT"),
}
