package com.linecorp.kotlinjdsl.dsl.sql.update.impl

import com.linecorp.kotlinjdsl.dsl.sql.update.UpdateQuerySetFirstStep
import com.linecorp.kotlinjdsl.dsl.sql.update.UpdateQuerySetMoreStep
import com.linecorp.kotlinjdsl.dsl.sql.update.UpdateQueryWhereStep
import com.linecorp.kotlinjdsl.query.sql.*
import com.linecorp.kotlinjdsl.query.sql.impl.And
import com.linecorp.kotlinjdsl.query.sql.impl.Or

class UpdateQueryDsl<T : Any> private constructor(
    private val builder: UpdateQueryBuilder<T>,
) : UpdateQuerySetFirstStep<T>,
    UpdateQuerySetMoreStep<T>,
    UpdateQueryWhereStep<T> {

    constructor(table: TableReference<T>) : this(UpdateQueryBuilder(table))

    override fun <V> set(column: Column<T, V>, expression: Expression<V>): UpdateQuerySetMoreStep<T> {
        builder.set(column, expression)

        return this
    }

    override fun where(predicate: Predicate): SqlQueryable<UpdateQuery<T>> {
        builder.where(predicate)

        return this
    }

    override fun whereAnd(vararg predicates: Predicate): SqlQueryable<UpdateQuery<T>> {
        builder.where(And(predicates.toList()))

        return this
    }

    override fun whereAnd(predicates: Collection<Predicate>): SqlQueryable<UpdateQuery<T>> {
        builder.where(And(predicates))

        return this
    }

    override fun whereOr(vararg predicates: Predicate): SqlQueryable<UpdateQuery<T>> {
        builder.where(Or(predicates.toList()))

        return this
    }

    override fun whereOr(predicates: Collection<Predicate>): SqlQueryable<UpdateQuery<T>> {
        builder.where(Or(predicates))

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
