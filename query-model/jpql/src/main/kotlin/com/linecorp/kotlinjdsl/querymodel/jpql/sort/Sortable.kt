package com.linecorp.kotlinjdsl.querymodel.jpql.sort

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
interface Sortable {
    @SinceJdsl("3.0.0")
    fun toSort(): Sort
}
