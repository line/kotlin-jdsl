package com.linecorp.kotlinjdsl.query.clause.having

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Subquery

interface SubqueryHavingClause {
    fun apply(froms: Froms, query: Subquery<*>, criteriaBuilder: CriteriaBuilder)
}