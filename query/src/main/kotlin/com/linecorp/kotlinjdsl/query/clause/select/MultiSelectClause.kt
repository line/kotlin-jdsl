package com.linecorp.kotlinjdsl.query.clause.select

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery

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