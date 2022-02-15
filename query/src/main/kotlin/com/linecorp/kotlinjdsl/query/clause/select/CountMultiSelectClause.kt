package com.linecorp.kotlinjdsl.query.clause.select

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
data class CountMultiSelectClause(
    val distinct: Boolean,
    val expressions: List<ExpressionSpec<*>>,
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
