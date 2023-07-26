package com.linecorp.kotlinjdsl.querymodel.jpql.join

import com.linecorp.kotlinjdsl.querymodel.jpql.from.Fromable

interface Joinable : Fromable {
    fun toJoin(): Join
}
