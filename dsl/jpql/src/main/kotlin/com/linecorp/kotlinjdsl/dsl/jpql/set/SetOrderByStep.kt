package com.linecorp.kotlinjdsl.dsl.jpql.set

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.set.SetQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sortable

/**
 * DSL step representing a set operation query where `ORDER BY` can be applied.
 */
@SinceJdsl("3.6.0")
interface SetOrderByStep<T : Any> : JpqlQueryable<SetQuery<T>> {
    /**
     * Creates an `ORDER BY` clause in the set operation query.
     *
     * @param sorts the sort criteria.
     */
    @SinceJdsl("3.6.0")
    fun orderBy(vararg sorts: Sortable?): SetQuery<T>

    /**
     * Creates an `ORDER BY` clause in the set operation query.
     *
     * @param sorts the sort criteria.
     */
    @SinceJdsl("3.6.0")
    fun orderBy(sorts: Iterable<Sortable?>): SetQuery<T>
}
