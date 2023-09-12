package com.linecorp.kotlinjdsl.querymodel.jpql.from

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
interface Fromable {
    @SinceJdsl("3.0.0")
    fun toFrom(): From
}
