package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Path

internal class SelectQueryFromStepDsl(
    private val select: Collection<Expression<*>>,
    private val distinct: Boolean,
) : SelectQueryFromStep {
    override fun from(vararg paths: Path<*>): SelectQueryWhereStep {
        return SelectQueryDsl(select, distinct, paths.toList())
    }

    override fun from(paths: Collection<Path<*>>): SelectQueryWhereStep {
        return SelectQueryDsl(select, distinct, paths)
    }
}
