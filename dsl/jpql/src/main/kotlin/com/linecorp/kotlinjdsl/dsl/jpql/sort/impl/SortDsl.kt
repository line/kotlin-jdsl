package com.linecorp.kotlinjdsl.dsl.jpql.sort.impl

import com.linecorp.kotlinjdsl.dsl.jpql.sort.SortNullsStep
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sortable

@PublishedApi
internal data class SortDsl(
    val builder: SortBuilder,
) : SortNullsStep {
    constructor(expr: Expression<*>, order: Sort.Order) : this(SortBuilder(expr, order))

    override fun nullsFirst(): Sortable {
        builder.nullsFirst()

        return this
    }

    override fun nullsLast(): Sortable {
        builder.nullsLast()

        return this
    }

    override fun toSort(): Sort {
        return builder.build()
    }
}
