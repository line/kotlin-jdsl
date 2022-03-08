package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.CriteriaDeleteQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaUpdateQuerySpec
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery

class CriteriaQueryCreatorImpl(
    private val em: EntityManager,
) : CriteriaQueryCreator {
    override fun <T> createQuery(spec: CriteriaQuerySpec<T, TypedQuery<T>>): TypedQuery<T> =
        createQuery(spec, em.criteriaBuilder, em::createQuery)

    override fun <T> createQuery(spec: CriteriaUpdateQuerySpec<T, Query>): Query =
        createQuery(spec, em.criteriaBuilder, em::createQuery)

    override fun <T> createQuery(spec: CriteriaDeleteQuerySpec<T, Query>): Query =
        createQuery(spec, em.criteriaBuilder, em::createQuery)
}
