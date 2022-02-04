package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Subquery

interface SubqueryCreator {
    fun <T> createQuery(
        spec: SubquerySpec<T>,
        froms: Froms,
        criteriaQuery: CriteriaQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Subquery<T>

    fun <T> createQuery(
        spec: SubquerySpec<T>,
        froms: Froms,
        subquery: Subquery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Subquery<T>
}