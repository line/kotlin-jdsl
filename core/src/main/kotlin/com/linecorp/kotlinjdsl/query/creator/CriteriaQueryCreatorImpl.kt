package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

class CriteriaQueryCreatorImpl(
    private val em: EntityManager,
) : CriteriaQueryCreator {
    override fun <T> createQuery(spec: CriteriaQuerySpec<T>): TypedQuery<T> {
        val criteriaBuilder = em.criteriaBuilder
        val query = criteriaBuilder.createQuery(spec.select.returnType)
        val froms = spec.from.join(spec.join, query)

        spec.select.apply(froms, query, criteriaBuilder)
        spec.where.apply(froms, query, criteriaBuilder)
        spec.groupBy.apply(froms, query, criteriaBuilder)
        spec.having.apply(froms, query, criteriaBuilder)
        spec.orderBy.apply(froms, query, criteriaBuilder)

        return em.createQuery(query).apply {
            spec.limit.apply(this)
            spec.jpaHint.apply(this)
            spec.sqlHint.apply(this)
        }
    }
}