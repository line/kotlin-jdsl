package com.linecorp.kotlinjdsl.querymodel.jpql.delete

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.impl.JpqlDeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

/**
 * Factory class that creates [DeleteQuery].
 */
@SinceJdsl("3.0.0")
object DeleteQueries {
    /**
     * Creates a delete query.
     */
    @SinceJdsl("3.0.0")
    fun <T : Any> deleteQuery(
        entity: Entity<T>,
        where: Predicate? = null,
    ): DeleteQuery<T> {
        return JpqlDeleteQuery(
            entity = entity,
            where = where,
        )
    }
}
