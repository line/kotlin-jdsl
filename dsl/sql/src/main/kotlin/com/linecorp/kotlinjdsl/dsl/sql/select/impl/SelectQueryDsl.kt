package com.linecorp.kotlinjdsl.dsl.sql.select.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.dsl.sql.select.*
import com.linecorp.kotlinjdsl.property.PropertyUtils
import com.linecorp.kotlinjdsl.querymodel.sql.*
import com.linecorp.kotlinjdsl.querymodel.sql.impl.And
import com.linecorp.kotlinjdsl.querymodel.sql.impl.Or
import kotlin.reflect.KProperty1

@Internal
class SelectQueryDsl private constructor(
    private val builder: SelectQueryBuilder,
) : SelectQueryFromStep,
    SelectQueryWhereStep,
    SelectQueryGroupByStep,
    SelectQueryHavingStep,
    SelectQueryOrderByStep,
    SelectQueryLimitStep {

    constructor(
        expressions: Iterable<Expression<*>>,
        distinct: Boolean,
    ) : this(
        SelectQueryBuilder(expressions).distinct(distinct),
    )

    override fun from(vararg tables: Table<*>): SelectQueryWhereStep {
        builder.from(tables.toList())

        return this
    }

    override fun from(tables: Iterable<Table<*>>): SelectQueryWhereStep {
        builder.from(tables)

        return this
    }

    override fun where(predicate: Predicate): SelectQueryGroupByStep {
        builder.where(predicate)

        return this
    }

    override fun whereAnd(vararg predicates: Predicate): SelectQueryGroupByStep {
        builder.where(And(predicates.toList()))

        return this
    }

    override fun whereAnd(predicates: Iterable<Predicate>): SelectQueryGroupByStep {
        builder.where(And(predicates))

        return this
    }

    override fun whereOr(vararg predicates: Predicate): SelectQueryGroupByStep {
        builder.where(Or(predicates.toList()))

        return this
    }

    override fun whereOr(predicates: Iterable<Predicate>): SelectQueryGroupByStep {
        builder.where(Or(predicates))

        return this
    }

    override fun groupBy(vararg properties: KProperty1<out Any, *>): SelectQueryHavingStep {
        val columns = properties.map {
            @Suppress("UNCHECKED_CAST")
            val casted = it as KProperty1<Any, *>

            Column(TableReference(PropertyUtils.getOwner(casted)), casted)
        }

        builder.groupBy(columns)

        return this
    }

    override fun groupBy(vararg expressions: Expression<*>): SelectQueryHavingStep {
        builder.groupBy(expressions.toList())

        return this
    }

    override fun groupBy(expressions: Iterable<Expression<*>>): SelectQueryHavingStep {
        builder.groupBy(expressions)

        return this
    }

    override fun having(predicate: Predicate): SelectQueryOrderByStep {
        builder.having(predicate)

        return this
    }

    override fun havingAnd(vararg predicates: Predicate): SelectQueryOrderByStep {
        builder.having(And(predicates.toList()))

        return this
    }

    override fun havingAnd(predicates: Iterable<Predicate>): SelectQueryOrderByStep {
        builder.having(And(predicates))

        return this
    }

    override fun havingOr(vararg predicates: Predicate): SelectQueryOrderByStep {
        builder.having(Or(predicates.toList()))

        return this
    }

    override fun havingOr(predicates: Iterable<Predicate>): SelectQueryOrderByStep {
        builder.having(Or(predicates))

        return this
    }

    override fun orderBy(vararg sorts: Sort): SelectQueryLimitStep {
        builder.orderBy(sorts.toList())

        return this
    }

    override fun orderBy(sorts: Iterable<Sort>): SelectQueryLimitStep {
        builder.orderBy(sorts)

        return this
    }

    override fun offset(offset: Int): SqlQueryable<SelectQuery> {
        builder.offset(offset)

        return this
    }

    override fun limit(limit: Int): SqlQueryable<SelectQuery> {
        builder.limit(limit)

        return this
    }

    override fun limit(offset: Int, limit: Int): SqlQueryable<SelectQuery> {
        builder.offset(offset).limit(limit)

        return this
    }

    override fun toQuery(): SelectQuery {
        return builder.build()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SelectQueryDsl

        return builder == other.builder
    }

    override fun hashCode(): Int {
        return builder.hashCode()
    }

    override fun toString(): String {
        return "SelectQueryDsl(" +
            "builder=$builder)"
    }
}
