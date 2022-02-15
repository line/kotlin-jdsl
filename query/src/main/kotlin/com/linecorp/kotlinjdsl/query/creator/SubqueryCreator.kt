package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.SubquerySpec
import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.CommonAbstractCriteria
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Subquery

/**
 * Internal Only
 * Don't use this directly because it's an <string>INTERNAL</strong>.
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
