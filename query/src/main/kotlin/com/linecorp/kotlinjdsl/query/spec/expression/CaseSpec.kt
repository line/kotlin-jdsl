package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.Expression

data class CaseSpec<T>(
    val whens: List<WhenSpec<out T>>,
    val `else`: ExpressionSpec<out T>,
) : ExpressionSpec<T> {
    override fun toCriteriaExpression(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return criteriaBuilder.selectCase<T>().apply {
            whens.forEach {
                `when`(
                    it.predicate.toCriteriaPredicate(froms, query, criteriaBuilder),
                    it.result.toCriteriaExpression(froms, query, criteriaBuilder),
                )
            }
        }.otherwise(`else`.toCriteriaExpression(froms, query, criteriaBuilder))
    }

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Expression<T> {
        return criteriaBuilder.selectCase<T>().apply {
            whens.forEach {
                `when`(
                    it.predicate.toCriteriaPredicate(froms, query, criteriaBuilder),
                    it.result.toCriteriaExpression(froms, query, criteriaBuilder),
                )
            }
        }.otherwise(`else`.toCriteriaExpression(froms, query, criteriaBuilder))
    }

    data class WhenSpec<T>(
        val predicate: PredicateSpec,
        val result: ExpressionSpec<out T>,
    )
}
