package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.spec.Froms
import jakarta.persistence.criteria.CommonAbstractCriteria
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Subquery

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
interface SubqueryCreator {
    fun <T> createQuery(
        spec: SubquerySpec<T>,
        froms: Froms,
        criteria: CommonAbstractCriteria,
        criteriaBuilder: CriteriaBuilder
    ): Subquery<T>
}
