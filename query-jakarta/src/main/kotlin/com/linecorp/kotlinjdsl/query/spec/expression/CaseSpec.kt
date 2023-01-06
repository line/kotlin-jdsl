package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import jakarta.persistence.criteria.*

data class CaseSpec<T>(
    private val whens: List<WhenSpec<out T>>,
    private val `else`: ExpressionSpec<out T>,
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

    override fun toCriteriaExpression(
        froms: Froms,
        query: CriteriaDelete<*>,
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
