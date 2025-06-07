package com.linecorp.kotlinjdsl.querymodel.jpql.set.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.set.SetOperationType // Enum for operation type
import com.linecorp.kotlinjdsl.querymodel.jpql.set.SetQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import kotlin.reflect.KClass

/**
 * Represents a JPQL set operation query (UNION, UNION ALL, INTERSECT, EXCEPT).
 * This is the actual query model.
 * @param T The DTO type returned by each row of the set operation.
 */
data class JpqlSetOperationQuery<T : Any>(
    val leftQuery: SelectQuery<T>,
    val operationType: SetOperationType,
    val rightQuery: SelectQuery<T>,
    val orderBy: Iterable<Sort>? = null, // ORDER BY for the entire set operation
) : SetQuery<T> { // Implements JpqlQuery of itself

    /**
     * The DTO type returned by each row of this set operation query.
     * This is derived from the left-hand side SelectQuery.
     */
    override val returnType: KClass<T> // Defines the DTO type for this query model
        get() = leftQuery.returnType // Assumes SelectQuery<T> has returnType

    /**
     * Returns this query model itself.
     * Required by JpqlQueryable (which JpqlQuery inherits).
     */
    override fun toQuery(): JpqlSetOperationQuery<T> = this
}
