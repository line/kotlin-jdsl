package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CommonAbstractCriteria
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Subquery

interface SubqueryCreator {
    fun <T> createQuery(
        spec: SubquerySpec<T>,
        froms: Froms,
        commonQuery: CommonAbstractCriteria,
        criteriaBuilder: CriteriaBuilder
    ): Subquery<T>
}
