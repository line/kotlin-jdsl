package com.linecorp.kotlinjdsl.querymodel.jpql.sort

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.QueryPart

@SinceJdsl("3.0.0")
interface Sort : Sortable, QueryPart {
    override fun toSort(): Sort = this

    enum class Order {
        ASC,
        DESC,
    }

    enum class NullOrder {
        FIRST,
        LAST,
    }
}
