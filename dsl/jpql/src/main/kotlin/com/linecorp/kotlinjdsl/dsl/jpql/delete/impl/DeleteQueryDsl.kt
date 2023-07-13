package com.linecorp.kotlinjdsl.dsl.jpql.delete.impl

import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDslSupport
import com.linecorp.kotlinjdsl.dsl.jpql.delete.DeleteQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate

internal class DeleteQueryDsl<T : Any> private constructor(
    private val builder: DeleteQueryBuilder<T>,
) : DeleteQueryWhereStep<T> {
    constructor(from: Path<T>) : this(DeleteQueryBuilder(from))

    override fun where(predicate: Predicate): JpqlQueryable<DeleteQuery<T>> {
        builder.where(predicate)

        return this
    }

    override fun whereAnd(vararg predicates: Predicate): JpqlQueryable<DeleteQuery<T>> {
        builder.where(JpqlDslSupport.and(predicates.toList()))

        return this
    }

    override fun whereAnd(predicates: Collection<Predicate>): JpqlQueryable<DeleteQuery<T>> {
        builder.where(JpqlDslSupport.and(predicates))

        return this
    }

    override fun whereOr(vararg predicates: Predicate): JpqlQueryable<DeleteQuery<T>> {
        builder.where(JpqlDslSupport.or(predicates.toList()))

        return this
    }

    override fun whereOr(predicates: Collection<Predicate>): JpqlQueryable<DeleteQuery<T>> {
        builder.where(JpqlDslSupport.or(predicates))

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
