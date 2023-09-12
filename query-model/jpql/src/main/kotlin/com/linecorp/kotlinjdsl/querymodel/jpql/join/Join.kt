package com.linecorp.kotlinjdsl.querymodel.jpql.join

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From

@SinceJdsl("3.0.0")
interface Join : From, Joinable, QueryPart {
    @SinceJdsl("3.0.0")
    val joinType: JoinType

    @SinceJdsl("3.0.0")
    val fetch: Boolean

    override fun toJoin(): Join = this
    override fun toFrom(): From = this
}
