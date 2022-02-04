package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Subquery

class SubqueryCreatorImpl : SubqueryCreator {
    override fun <T> createQuery(
        spec: SubquerySpec<T>,
        froms: Froms,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Subquery<T> {
        val query = criteriaQuery.subquery(spec.select.returnType)

        return apply(spec, froms, query, criteriaBuilder)
    }

    override fun <T> createQuery(
        spec: SubquerySpec<T>,
        froms: Froms,
        subquery: Subquery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Subquery<T> {
        val query = subquery.subquery(spec.select.returnType)

        return apply(spec, froms, query, criteriaBuilder)
    }

    private fun <T> apply(
        spec: SubquerySpec<T>,
        froms: Froms,
        query: Subquery<T>,
        criteriaBuilder: CriteriaBuilder
    ): Subquery<T> {
        val subqueryFroms = spec.from.join(spec.join, query) + froms

        spec.select.apply(subqueryFroms, query, criteriaBuilder)
        spec.where.apply(subqueryFroms, query, criteriaBuilder)
        spec.groupBy.apply(subqueryFroms, query, criteriaBuilder)
        spec.having.apply(subqueryFroms, query, criteriaBuilder)

        return query
    }
}