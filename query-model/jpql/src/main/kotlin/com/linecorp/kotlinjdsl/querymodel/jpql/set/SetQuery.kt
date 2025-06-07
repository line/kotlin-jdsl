package com.linecorp.kotlinjdsl.querymodel.jpql.set

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import kotlin.reflect.KClass

/**
 * DSL entry point for a set operation query (UNION, UNION ALL, INTERSECT, EXCEPT).
 * This query currently supports the `ORDER BY` clause.
 * Further capabilities to use this query as a subquery for subsequent SELECT, WHERE, GROUP BY, HAVING clauses are planned.
 */
@SinceJdsl("3.6.0")
interface SetQuery<T : Any> : JpqlQuery<SetQuery<T>> {
    @SinceJdsl("3.6.0")
    val returnType: KClass<T>
}
