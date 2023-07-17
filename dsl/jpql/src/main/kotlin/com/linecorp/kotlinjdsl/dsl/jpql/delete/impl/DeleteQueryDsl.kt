package com.linecorp.kotlinjdsl.dsl.jpql.delete.impl

import com.linecorp.kotlinjdsl.dsl.jpql.delete.DeleteQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

internal class DeleteQueryDsl<T : Any> private constructor(
    private val builder: DeleteQueryBuilder<T>,
) : DeleteQueryWhereStep<T> {
    constructor(from: Path<T>) : this(DeleteQueryBuilder(from))

    override fun where(predicate: Predicate): JpqlQueryable<DeleteQuery<T>> {
        builder.where(predicate)

        return this
    }

    override fun whereAnd(vararg predicates: Predicate?): JpqlQueryable<DeleteQuery<T>> {
        builder.where(Predicates.and(predicates.toList()))

        return this
    }

    override fun whereAnd(predicates: Collection<Predicate?>): JpqlQueryable<DeleteQuery<T>> {
        builder.where(Predicates.and(predicates))

        return this
    }

    override fun whereOr(vararg predicates: Predicate?): JpqlQueryable<DeleteQuery<T>> {
        builder.where(Predicates.or(predicates.toList()))

        return this
    }

    override fun whereOr(predicates: Collection<Predicate?>): JpqlQueryable<DeleteQuery<T>> {
        builder.where(Predicates.or(predicates))

        return this
    }

    override fun toQuery(): DeleteQuery<T> {
        return builder.build()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DeleteQueryDsl<*>

        return builder == other.builder
    }

    override fun hashCode(): Int {
        return builder.hashCode()
    }

    override fun toString(): String {
        return "DeleteQueryDsl(" +
            "builder=$builder)"
    }
}
