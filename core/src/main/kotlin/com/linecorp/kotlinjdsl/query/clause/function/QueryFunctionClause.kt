package com.linecorp.kotlinjdsl.query.clause.function

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder

interface QueryFunctionClause {
    fun apply(froms: Froms, query: AbstractQuery<*>, criteriaBuilder: CriteriaBuilder)
}
