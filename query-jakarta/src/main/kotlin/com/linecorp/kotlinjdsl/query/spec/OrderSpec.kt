package com.linecorp.kotlinjdsl.query.spec

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Order

interface OrderSpec {
    fun toCriteriaOrder(
        froms: Froms,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder,
    ): List<Order>
}
