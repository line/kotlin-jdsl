package com.linecorp.kotlinjdsl.dsl.jpql.update.impl

import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDslSupport
import com.linecorp.kotlinjdsl.dsl.jpql.update.UpdateQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.*

internal class UpdateQueryDsl<T : Any> private constructor(
    private val builder: UpdateQueryBuilder<T>
) : UpdateQueryWhereStep<T> {
    constructor(entity: Path<T>, set: Map<Path<*>, Expression<*>>) : this(UpdateQueryBuilder(entity, set))

    override fun where(predicate: Predicate): JpqlQueryable<UpdateQuery<T>> {
        builder.where(predicate)

        return this
    }

    override fun whereAnd(vararg predicates: Predicate): JpqlQueryable<UpdateQuery<T>> {
        builder.where(JpqlDslSupport.and(predicates.toList()))

        return this
    }

    override fun whereAnd(predicates: Collection<Predicate>): JpqlQueryable<UpdateQuery<T>> {
        builder.where(JpqlDslSupport.and(predicates.toList()))

        return this
    }

    override fun whereOr(vararg predicates: Predicate): JpqlQueryable<UpdateQuery<T>> {
        builder.where(JpqlDslSupport.or(predicates.toList()))

        return this
    }

    override fun whereOr(predicates: Collection<Predicate>): JpqlQueryable<UpdateQuery<T>> {
        builder.where(JpqlDslSupport.or(predicates.toList()))

        return this
    }

    override fun toQuery(): UpdateQuery<T> {
        return builder.build()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UpdateQueryDsl<*>

        return builder == other.builder
    }

    override fun hashCode(): Int {
        return builder.hashCode()
    }

    override fun toString(): String {
        return "UpdateQueryDsl(" +
            "builder=$builder)"
    }
}
