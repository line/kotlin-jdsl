package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Expression

data class FunctionSpec<T>(
    val name: String,
    val returnType: Class<T>,
    val expressions: List<ExpressionSpec<*>>,
) : ExpressionSpec<T> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return criteriaBuilder.function(
            name,
            returnType,
            *expressions.map { it.toCriteriaExpression(froms, query, criteriaBuilder) }.toTypedArray()
        )
    }
}
