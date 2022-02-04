package com.linecorp.kotlinjdsl.query.clause.groupby

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Subquery

interface SubqueryGroupByClause {
    fun apply(froms: Froms, query: Subquery<*>, criteriaBuilder: CriteriaBuilder)
}