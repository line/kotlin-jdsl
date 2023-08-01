package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryGroupByStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryHavingStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryOrderByStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicatable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sortable
import kotlin.reflect.KClass

@PublishedApi
internal data class SelectQueryDsl<T : Any>(
    private val builder: SelectQueryBuilder<T>,
) : SelectQueryWhereStep<T>,
    SelectQueryGroupByStep<T>,
    SelectQueryHavingStep<T>,
    SelectQueryOrderByStep<T> {

    constructor(
        returnType: KClass<T>,
        distinct: Boolean,
        select: Iterable<Expression<*>>,
        from: Iterable<From>,
    ) : this(
        SelectQueryBuilder<T>(returnType, distinct, select, from),
    )

    override fun where(predicate: Predicatable?): SelectQueryGroupByStep<T> {
        if (predicate != null) {
            builder.where(predicate.toPredicate())
        }

        return this
    }

    override fun whereAnd(vararg predicates: Predicatable?): SelectQueryGroupByStep<T> {
        builder.where(Predicates.and(predicates.mapNotNull { it?.toPredicate() }))

        return this
    }

    override fun whereOr(vararg predicates: Predicatable?): SelectQueryGroupByStep<T> {
        builder.where(Predicates.or(predicates.mapNotNull { it?.toPredicate() }))

        return this
    }

    override fun groupBy(vararg expr: Expressionable<*>?): SelectQueryHavingStep<T> {
        builder.groupBy(expr.mapNotNull { it?.toExpression() })

        return this
    }

    override fun having(predicate: Predicatable?): SelectQueryOrderByStep<T> {
        if (predicate != null) {
            builder.having(predicate.toPredicate())
        }

        return this
    }

    override fun havingAnd(vararg predicates: Predicatable?): SelectQueryOrderByStep<T> {
        builder.having(Predicates.and(predicates.mapNotNull { it?.toPredicate() }))

        return this
    }

    override fun havingOr(vararg predicates: Predicatable?): SelectQueryOrderByStep<T> {
        builder.having(Predicates.or(predicates.mapNotNull { it?.toPredicate() }))

        return this
    }

    override fun orderBy(vararg sorts: Sortable?): JpqlQueryable<SelectQuery<T>> {
        builder.orderBy(sorts.mapNotNull { it?.toSort() })

        return this
    }

    override fun toQuery(): SelectQuery<T> {
        return builder.build()
    }
}
