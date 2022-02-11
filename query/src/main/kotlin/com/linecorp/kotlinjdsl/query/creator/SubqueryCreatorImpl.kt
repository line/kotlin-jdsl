package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.*

class SubqueryCreatorImpl : SubqueryCreator {
    override fun <T> createQuery(
        spec: SubquerySpec<T>,
        froms: Froms,
        commonQuery: CommonAbstractCriteria,
        criteriaBuilder: CriteriaBuilder
    ): Subquery<T> {
        val subquery = commonQuery.subquery(spec.select.returnType)
        val subqueryFroms = spec.from.join(spec.join, subquery) + froms

        spec.select.apply(subqueryFroms, subquery, criteriaBuilder)
        spec.where.apply(subqueryFroms, subquery, criteriaBuilder)
        spec.groupBy.apply(subqueryFroms, subquery, criteriaBuilder)
        spec.having.apply(subqueryFroms, subquery, criteriaBuilder)

        return subquery
    }
}
