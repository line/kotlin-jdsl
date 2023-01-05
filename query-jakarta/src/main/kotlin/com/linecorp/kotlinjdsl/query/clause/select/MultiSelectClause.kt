package com.linecorp.kotlinjdsl.query.clause.select

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
data class MultiSelectClause<T>(
    override val returnType: Class<T>,
    val distinct: Boolean,
    val expressions: List<ExpressionSpec<*>>,
) : CriteriaQuerySelectClause<T> {
    override fun apply(froms: Froms, query: CriteriaQuery<T>, criteriaBuilder: CriteriaBuilder) {
        val criteriaExpression = criteriaBuilder.construct(
            returnType,
            *expressions.map { it.toCriteriaExpression(froms, query, criteriaBuilder) }.toTypedArray()
        )

        query.select(criteriaExpression).distinct(distinct)
    }
}
