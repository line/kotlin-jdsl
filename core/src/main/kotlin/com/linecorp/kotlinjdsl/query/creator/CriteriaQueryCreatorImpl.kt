package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaUpdateQuerySpec
import javax.persistence.EntityManager
import javax.persistence.Query
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaUpdate

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

    @Suppress("UNCHECKED_CAST")
    override fun <T> createQuery(spec: CriteriaUpdateQuerySpec<T>): Query {
        val criteriaBuilder = em.criteriaBuilder
        val query = criteriaBuilder.createCriteriaUpdate(spec.targetEntity) as CriteriaUpdate<Any>
        val froms = spec.from.associate(spec.associate, query, spec.targetEntity)

        spec.where.apply(froms, query, criteriaBuilder)
        spec.set.apply(froms, query, criteriaBuilder)

        return em.createQuery(query).apply {
            spec.jpaHint.apply(this)
            spec.sqlHint.apply(this)
        }
    }

}
