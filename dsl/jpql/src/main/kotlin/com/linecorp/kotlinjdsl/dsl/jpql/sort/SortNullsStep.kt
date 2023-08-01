package com.linecorp.kotlinjdsl.dsl.jpql.sort

import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sortable

interface SortNullsStep : Sortable {
    fun nullsFirst(): Sortable

    fun nullsLast(): Sortable
}
