package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryGroupByStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryHavingStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryOrderByStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.*
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.And
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Or

class SelectQueryDsl private constructor(
    private val builder: SelectQueryBuilder,
) : SelectQueryWhereStep,
    SelectQueryGroupByStep,
    SelectQueryHavingStep,
    SelectQueryOrderByStep {

    constructor(
        select: Collection<Expression<*>>,
        distinct: Boolean,
        from: Collection<Path<*>>,
    ) : this(
        SelectQueryBuilder(select, distinct, from),
    )

    override fun where(predicate: Predicate): SelectQueryGroupByStep {
        builder.where(predicate)

        return this
    }

    override fun whereAnd(vararg predicates: Predicate): SelectQueryGroupByStep {
        builder.where(And(predicates.toList()))

        return this
    }

    override fun whereAnd(predicates: Collection<Predicate>): SelectQueryGroupByStep {
        builder.where(And(predicates))

        return this
    }

    override fun whereOr(vararg predicates: Predicate): SelectQueryGroupByStep {
        builder.where(Or(predicates.toList()))

        return this
    }

    override fun whereOr(predicates: Collection<Predicate>): SelectQueryGroupByStep {
        builder.where(Or(predicates))

        return this
    }

    override fun groupBy(vararg expressions: Expressionable<*>): SelectQueryHavingStep {
        builder.groupBy(expressions.map { it.toExpression() })

        return this
    }

    override fun groupBy(expressions: Collection<Expressionable<*>>): SelectQueryHavingStep {
        builder.groupBy(expressions.map { it.toExpression() })

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

    override fun havingAnd(predicates: Collection<Predicate>): SelectQueryOrderByStep {
        builder.having(And(predicates))

        return this
    }

    override fun havingOr(vararg predicates: Predicate): SelectQueryOrderByStep {
        builder.having(Or(predicates.toList()))

        return this
    }

    override fun havingOr(predicates: Collection<Predicate>): SelectQueryOrderByStep {
        builder.having(Or(predicates))

        return this
    }

    override fun orderBy(vararg sorts: Sort): JpqlQueryable<SelectQuery> {
        builder.orderBy(sorts.toList())

        return this
    }

    override fun orderBy(sorts: Collection<Sort>): JpqlQueryable<SelectQuery> {
        builder.orderBy(sorts)

        return this
    }

    override fun toQuery(): SelectQuery {
        return builder.build()
    }
}
