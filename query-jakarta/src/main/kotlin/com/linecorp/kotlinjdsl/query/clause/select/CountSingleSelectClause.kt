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
data class CountSingleSelectClause(
    private val distinct: Boolean,
    private val expression: ExpressionSpec<*>,
) : CriteriaQuerySelectClause<Long> {
    override val returnType: Class<Long> = Long::class.java

    override fun apply(froms: Froms, query: CriteriaQuery<Long>, criteriaBuilder: CriteriaBuilder) {
        val criteriaExpression = expression.toCriteriaExpression(froms, query, criteriaBuilder)

        if (distinct) {
            query.select(criteriaBuilder.countDistinct(criteriaExpression))
        } else {
            query.select(criteriaBuilder.count(criteriaExpression))
        }
    }
}
