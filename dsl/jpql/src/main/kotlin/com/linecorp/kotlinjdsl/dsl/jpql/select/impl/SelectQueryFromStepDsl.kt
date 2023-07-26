package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.from.Fromable
import kotlin.reflect.KClass

internal data class SelectQueryFromStepDsl<T : Any>(
    private val returnType: KClass<*>,
    private val distinct: Boolean,
    private val select: Iterable<Expression<*>>,
) : SelectQueryFromStep<T> {
    override fun from(vararg froms: Fromable?): SelectQueryWhereStep<T> {
        return SelectQueryDsl(returnType, distinct, select, froms.mapNotNull { it?.toFrom() })
    }

    override fun from(froms: Iterable<Fromable?>): SelectQueryWhereStep<T> {
        return SelectQueryDsl(returnType, distinct, select, froms.mapNotNull { it?.toFrom() })
    }
}
