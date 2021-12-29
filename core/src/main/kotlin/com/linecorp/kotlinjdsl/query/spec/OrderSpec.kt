package com.linecorp.kotlinjdsl.query.spec

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Order

interface OrderSpec {
    fun toCriteriaOrder(
        froms: Froms,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder,
    ): List<Order>
}