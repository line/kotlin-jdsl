package com.linecorp.kotlinjdsl.querymodel.jpql.update

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.update.impl.JpqlUpdateQuery

@SinceJdsl("3.0.0")
object UpdateQueries {
    @SinceJdsl("3.0.0")
    fun <T : Any> updateQuery(
        entity: Entity<T>,
        set: Map<Path<*>, Expression<*>>,
        where: Predicate? = null,
    ): UpdateQuery<T> {
        return JpqlUpdateQuery(
            entity = entity,
            set = set,
            where = where,
        )
    }
}
