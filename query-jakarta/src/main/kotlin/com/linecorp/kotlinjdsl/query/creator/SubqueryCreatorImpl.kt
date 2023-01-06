package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.spec.Froms
import jakarta.persistence.criteria.CommonAbstractCriteria
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Subquery

class SubqueryCreatorImpl : SubqueryCreator {
    override fun <T> createQuery(
        spec: SubquerySpec<T>,
        froms: Froms,
        criteria: CommonAbstractCriteria,
        criteriaBuilder: CriteriaBuilder
    ): Subquery<T> {
        val subquery = criteria.subquery(spec.select.returnType)
        val subqueryFroms = spec.from.join(spec.join, subquery, criteriaBuilder) + froms

        spec.select.apply(subqueryFroms, subquery, criteriaBuilder)
        spec.where.apply(subqueryFroms, subquery, criteriaBuilder)
        spec.groupBy.apply(subqueryFroms, subquery, criteriaBuilder)
        spec.having.apply(subqueryFroms, subquery, criteriaBuilder)

        return subquery
    }
}
