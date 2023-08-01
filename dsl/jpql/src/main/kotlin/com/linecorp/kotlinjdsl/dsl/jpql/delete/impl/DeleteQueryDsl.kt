package com.linecorp.kotlinjdsl.dsl.jpql.delete.impl

import com.linecorp.kotlinjdsl.dsl.jpql.delete.DeleteQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates

@PublishedApi
internal data class DeleteQueryDsl<T : Any>(
    private val builder: DeleteQueryBuilder<T>,
) : DeleteQueryWhereStep<T> {
    constructor(entity: Entity<T>) : this(DeleteQueryBuilder(entity))

    override fun where(predicate: Predicatable?): JpqlQueryable<DeleteQuery<T>> {
        if (predicate != null) {
            builder.where(predicate.toPredicate())
        }

        return this
    }

    override fun whereAnd(vararg predicates: Predicatable?): JpqlQueryable<DeleteQuery<T>> {
        builder.where(Predicates.and(predicates.mapNotNull { it?.toPredicate() }))

        return this
    }

    override fun whereOr(vararg predicates: Predicatable?): JpqlQueryable<DeleteQuery<T>> {
        builder.where(Predicates.or(predicates.mapNotNull { it?.toPredicate() }))

        return this
    }

    override fun toQuery(): DeleteQuery<T> {
        return builder.build()
    }
}
