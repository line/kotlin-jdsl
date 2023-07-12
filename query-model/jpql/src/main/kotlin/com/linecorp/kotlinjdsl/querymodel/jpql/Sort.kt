package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.QueryPart

@SinceJdsl("3.0.0")
interface Sort : QueryPart {
    val order: Order

    enum class Order {
        ASC,
        DESC,
    }
}
