package com.linecorp.kotlinjdsl.dsl.jpql.set.impl

import com.linecorp.kotlinjdsl.dsl.jpql.set.SetOrderByStep
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.set.SetOperationType
import com.linecorp.kotlinjdsl.querymodel.jpql.set.SetQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.set.impl.JpqlSetOperationQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sortable

/**
 * DSL implementation for set operations.
 * Implements [SetQuery] to provide a fluent API for [JpqlSetOperationQuery].
 * @param T The DTO type returned by each row of the set operation.
 */
@PublishedApi
internal data class SetQueryDsl<T : Any>(
    private val leftQuery: SelectQuery<T>,
    private val operationType: SetOperationType,
    private val rightQuery: SelectQuery<T>,
    private var orderBy: List<Sort>? = null, // Internal state for orderBy clauses
) : SetQuery<T>, SetOrderByStep<T> { // Implements the SetQuery DSL interface

    override fun orderBy(vararg sorts: Sortable?): SetQuery<T> {
        this.orderBy = sorts.filterNotNull().map { it.toSort() }
        return this // Returns itself, as SetQueryDsl implements SetQuery
    }

    override fun orderBy(sorts: Iterable<Sortable?>): SetQuery<T> {
        this.orderBy = sorts.filterNotNull().map { it.toSort() }
        return this // Returns itself, as SetQueryDsl implements SetQuery
    }

    override fun toQuery(): JpqlSetOperationQuery<T> {
        return JpqlSetOperationQuery(
            leftQuery = this.leftQuery,
            operationType = this.operationType,
            rightQuery = this.rightQuery,
            orderBy = this.orderBy,
        )
    }

    override val returnType = leftQuery.returnType
}
