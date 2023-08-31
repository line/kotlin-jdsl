package com.linecorp.kotlinjdsl.dsl.jpql.sort

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sortable

@SinceJdsl("3.0.0")
interface SortNullsStep : Sortable {
    @SinceJdsl("3.0.0")
    fun nullsFirst(): Sortable

    @SinceJdsl("3.0.0")
    fun nullsLast(): Sortable
}
