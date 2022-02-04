package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression

data class CountSpec<T>(
    val distinct: Boolean = false,
    val column: ColumnSpec<T>,
) : ExpressionSpec<Long> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<Long> {
        val jpaExpression = column.toCriteriaExpression(froms, query, criteriaBuilder)

        return if (distinct) {
            criteriaBuilder.countDistinct(jpaExpression)
        } else {
            criteriaBuilder.count(jpaExpression)
        }
    }
}

