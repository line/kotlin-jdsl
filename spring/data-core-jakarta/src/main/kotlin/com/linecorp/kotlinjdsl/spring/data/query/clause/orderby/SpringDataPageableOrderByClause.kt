package com.linecorp.kotlinjdsl.spring.data.query.clause.orderby

import com.linecorp.kotlinjdsl.query.clause.orderby.CriteriaQueryOrderByClause
import com.linecorp.kotlinjdsl.query.spec.Froms
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.query.QueryUtils
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery

data class SpringDataPageableOrderByClause(
    private val pageable: Pageable
) : CriteriaQueryOrderByClause {
    override fun apply(froms: Froms, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder) {
        query.orderBy(QueryUtils.toOrders(pageable.sort, froms.root, criteriaBuilder))
    }
}
