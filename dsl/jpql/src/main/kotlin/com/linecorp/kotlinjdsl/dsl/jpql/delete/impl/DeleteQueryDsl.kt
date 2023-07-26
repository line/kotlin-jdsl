package com.linecorp.kotlinjdsl.dsl.jpql.delete.impl

import com.linecorp.kotlinjdsl.dsl.jpql.delete.DeleteQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates

internal data class DeleteQueryDsl<T : Any>(
    private val builder: DeleteQueryBuilder<T>,
) : DeleteQueryWhereStep<T> {
    constructor(entity: Entity<T>) : this(DeleteQueryBuilder(entity))

    override fun where(predicate: Predicate?): JpqlQueryable<DeleteQuery<T>> {
        if (predicate != null) {
            builder.where(predicate)
        }

        return this
    }

    override fun whereAnd(vararg predicates: Predicate?): JpqlQueryable<DeleteQuery<T>> {
        builder.where(Predicates.and(predicates.toList()))

        return this
    }

    override fun whereAnd(predicates: Iterable<Predicate?>): JpqlQueryable<DeleteQuery<T>> {
        builder.where(Predicates.and(predicates))

        return this
    }

    override fun whereOr(vararg predicates: Predicate?): JpqlQueryable<DeleteQuery<T>> {
        builder.where(Predicates.or(predicates.toList()))

        return this
    }

    override fun whereOr(predicates: Iterable<Predicate?>): JpqlQueryable<DeleteQuery<T>> {
        builder.where(Predicates.or(predicates))

        return this
    }

    override fun toQuery(): DeleteQuery<T> {
        return builder.build()
    }
}
