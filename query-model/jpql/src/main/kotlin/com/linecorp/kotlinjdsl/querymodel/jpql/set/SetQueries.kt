package com.linecorp.kotlinjdsl.querymodel.jpql.set

// No imports from dsl module needed here anymore
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.set.impl.JpqlSetOperationQuery // Model class

/**
 * Factory for creating [JpqlSetOperationQuery] model objects.
 * This object resides in the `query-model` module and only deals with query models.
 */
object SetQueries {
    fun <T : Any> union(
        left: SelectQuery<T>,
        right: SelectQuery<T>,
    ): JpqlSetOperationQuery<T> { // Returns the query model directly
        return JpqlSetOperationQuery(
            leftQuery = left,
            operationType = SetOperationType.UNION,
            rightQuery = right,
        )
    }

    fun <T : Any> unionAll(
        left: SelectQuery<T>,
        right: SelectQuery<T>,
    ): JpqlSetOperationQuery<T> { // Returns the query model directly
        return JpqlSetOperationQuery(
            leftQuery = left,
            operationType = SetOperationType.UNION_ALL,
            rightQuery = right,
        )
    }
}
