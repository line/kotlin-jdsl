package com.linecorp.kotlinjdsl.querymodel.jpql.join

import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From

interface Join : From, Joinable, QueryPart {
    val joinType: JoinType
    val fetch: Boolean

    override fun toJoin(): Join = this
    override fun toFrom(): From = this
}
