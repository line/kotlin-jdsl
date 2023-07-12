package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDslSupport
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryGroupByStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryHavingStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryOrderByStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.*
import kotlin.reflect.KClass

class SelectQueryDsl<T> private constructor(
    private val builder: SelectQueryBuilder<T>,
) : SelectQueryWhereStep<T>,
    SelectQueryGroupByStep<T>,
    SelectQueryHavingStep<T>,
    SelectQueryOrderByStep<T> {

    constructor(
        returnType: KClass<*>,
        select: Collection<Expression<*>>,
        distinct: Boolean,
        from: Collection<Path<*>>,
    ) : this(
        SelectQueryBuilder<T>(returnType, select, distinct, from),
    )

    override fun where(predicate: Predicatable): SelectQueryGroupByStep<T> {
        builder.where(predicate.toPredicate())

        return this
    }

    override fun whereAnd(vararg predicates: Predicatable): SelectQueryGroupByStep<T> {
        builder.where(JpqlDslSupport.and(predicates.toList()))

        return this
    }

    override fun whereAnd(predicates: Collection<Predicatable>): SelectQueryGroupByStep<T> {
        builder.where(JpqlDslSupport.and(predicates))

        return this
    }

    override fun whereOr(vararg predicates: Predicatable): SelectQueryGroupByStep<T> {
        builder.where(JpqlDslSupport.or(predicates.toList()))

        return this
    }

    override fun whereOr(predicates: Collection<Predicatable>): SelectQueryGroupByStep<T> {
        builder.where(JpqlDslSupport.or(predicates))

        return this
    }

    override fun groupBy(vararg expressions: Expressionable<*>): SelectQueryHavingStep<T> {
        builder.groupBy(expressions.map { it.toExpression() })

        return this
    }

    override fun groupBy(expressions: Collection<Expressionable<*>>): SelectQueryHavingStep<T> {
        builder.groupBy(expressions.map { it.toExpression() })

        return this
    }

    override fun having(predicate: Predicatable): SelectQueryOrderByStep<T> {
        builder.having(predicate.toPredicate())

        return this
    }

    override fun havingAnd(vararg predicates: Predicatable): SelectQueryOrderByStep<T> {
        builder.having(JpqlDslSupport.and(predicates.toList()))

        return this
    }

    override fun havingAnd(predicates: Collection<Predicatable>): SelectQueryOrderByStep<T> {
        builder.having(JpqlDslSupport.and(predicates))

        return this
    }

    override fun havingOr(vararg predicates: Predicatable): SelectQueryOrderByStep<T> {
        builder.having(JpqlDslSupport.or(predicates.toList()))

        return this
    }

    override fun havingOr(predicates: Collection<Predicatable>): SelectQueryOrderByStep<T> {
        builder.having(JpqlDslSupport.or(predicates))

        return this
    }

    override fun orderBy(vararg sorts: Sort): JpqlQueryable<SelectQuery<T>> {
        builder.orderBy(sorts.toList())

        return this
    }

    override fun orderBy(sorts: Collection<Sort>): JpqlQueryable<SelectQuery<T>> {
        builder.orderBy(sorts)

        return this
    }

    override fun toQuery(): SelectQuery<T> {
        return builder.build()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SelectQueryDsl<*>

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
