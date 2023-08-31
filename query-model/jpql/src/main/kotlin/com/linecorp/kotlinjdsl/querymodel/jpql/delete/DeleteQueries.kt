package com.linecorp.kotlinjdsl.querymodel.jpql.delete

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.impl.JpqlDeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@SinceJdsl("3.0.0")
object DeleteQueries {
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
