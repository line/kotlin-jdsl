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
data class CountMultiSelectClause(
    private val distinct: Boolean,
    private val expressions: List<ExpressionSpec<*>>,
) : CriteriaQuerySelectClause<Long> {
    override val returnType: Class<Long> = Long::class.java

    override fun apply(froms: Froms, query: CriteriaQuery<Long>, criteriaBuilder: CriteriaBuilder) {
        if (distinct) {
            throw IllegalStateException(
                "Count distinct does not support multiple columns. Please remove distinct in selection"
            )
        }

        query.select(criteriaBuilder.count(criteriaBuilder.literal(1)))
    }
}
