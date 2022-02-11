package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.*

data class SubqueryExpressionSpec<T>(
    val spec: SubquerySpec<T>,
    val subqueryCreator: SubqueryCreator,
) : ExpressionSpec<T> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return subqueryCreator.createQuery(spec, froms, query, criteriaBuilder)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return subqueryCreator.createQuery(spec, froms, query, criteriaBuilder)
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return subqueryCreator.createQuery(spec, froms, query, criteriaBuilder)
    }
}
