package com.linecorp.kotlinjdsl.query.clause.groupby

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery

interface CriteriaQueryGroupByClause {
    fun apply(froms: Froms, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder)
}