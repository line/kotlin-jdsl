package com.linecorp.kotlinjdsl.querymodel.jpql.join

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.from.Fromable

@SinceJdsl("3.0.0")
interface Joinable : Fromable {
    @SinceJdsl("3.0.0")
    fun toJoin(): Join
}
