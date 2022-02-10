package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.Expression

data class NullLiteralSpec<T>(
    val type: Class<T>,
) : ExpressionSpec<T?> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T?> = criteriaBuilder.nullLiteral(type)

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T?> = criteriaBuilder.nullLiteral(type)
}
