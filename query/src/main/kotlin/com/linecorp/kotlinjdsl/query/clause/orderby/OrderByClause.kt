package com.linecorp.kotlinjdsl.query.clause.orderby

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.OrderSpec
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
 * It does not support backward compatibility.
 */
data class OrderByClause(
    private val orders: List<OrderSpec>,
) : CriteriaQueryOrderByClause {
    companion object {
        val empty = OrderByClause(emptyList())
    }

    override fun apply(froms: Froms, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder) {
        if (orders.isEmpty()) return

        query.orderBy(orders.flatMap { it.toCriteriaOrder(froms, query, criteriaBuilder) })
    }
}
