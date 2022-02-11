package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.*

data class LiteralSpec<T : Any>(
    val value: T,
) : ExpressionSpec<T> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> = criteriaBuilder.literal(value)

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> = criteriaBuilder.literal(value)

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> = criteriaBuilder.literal(value)
}
