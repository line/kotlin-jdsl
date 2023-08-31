package com.linecorp.kotlinjdsl.dsl.jpql.delete.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

internal data class DeleteQueryBuilder<T : Any>(
    private val entity: Entity<T>,
    private var where: Predicate? = null,
) {
    fun where(predicate: Predicate): DeleteQueryBuilder<T> {
        where = predicate

        return this
    }

    fun build(): DeleteQuery<T> {
        return DeleteQueries.deleteQuery(
            entity = entity,
            where = where,
        )
    }
}
