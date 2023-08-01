package com.linecorp.kotlinjdsl.dsl.jpql.select.impl

import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryFromStep
import com.linecorp.kotlinjdsl.dsl.jpql.select.SelectQueryWhereStep
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.from.Fromable
import kotlin.reflect.KClass

@PublishedApi
internal data class SelectQueryFromStepDsl<T : Any>(
    private val returnType: KClass<T>,
    private val distinct: Boolean,
    private val select: Iterable<Expression<*>>,
) : SelectQueryFromStep<T> {
    override fun from(entity: Entity<*>): SelectQueryWhereStep<T> {
        return SelectQueryDsl(returnType, distinct, select, listOf(entity))
    }

    override fun from(entity: Entity<*>, vararg froms: Fromable?): SelectQueryWhereStep<T> {
        return SelectQueryDsl(returnType, distinct, select, listOf(entity) + froms.mapNotNull { it?.toFrom() })
    }
}
