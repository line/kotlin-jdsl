package com.linecorp.kotlinjdsl.query.clause.set

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaUpdate
import jakarta.persistence.criteria.Path

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
data class SetClause(private val params: Map<ColumnSpec<*>, Any?>) {
    fun apply(froms: Froms, query: CriteriaUpdate<*>, criteriaBuilder: CriteriaBuilder) {
        params.forEach {
            @Suppress("UNCHECKED_CAST")
            query.set(
                it.key.toCriteriaExpression(froms, query, criteriaBuilder) as Path<Any>,
                it.value
            )
        }
    }
}
