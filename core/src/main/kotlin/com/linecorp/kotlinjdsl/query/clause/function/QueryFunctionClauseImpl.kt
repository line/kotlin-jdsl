package com.linecorp.kotlinjdsl.query.clause.function

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.FunctionSpec
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder

class QueryFunctionClauseImpl<T>(private val spec: FunctionSpec<T>) : QueryFunctionClause {
    override fun apply(froms: Froms, query: AbstractQuery<*>, criteriaBuilder: CriteriaBuilder) {
        spec.toCriteriaExpression(froms, query, criteriaBuilder)
    }
}
