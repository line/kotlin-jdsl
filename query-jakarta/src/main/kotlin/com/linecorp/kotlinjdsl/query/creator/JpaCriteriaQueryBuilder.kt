package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.CriteriaDeleteQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaUpdateQuerySpec
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaDelete
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.CriteriaUpdate

object JpaCriteriaQueryBuilder {
    fun <T, Q> createQuery(
        spec: CriteriaQuerySpec<T, Q>,
        criteriaBuilder: CriteriaBuilder,
        queryBuilder: (CriteriaQuery<T>) -> Q
    ): Q {
        val query: CriteriaQuery<T> = criteriaBuilder.createQuery(spec.select.returnType)
        val froms = spec.from.join(spec.join, query, criteriaBuilder)

        spec.select.apply(froms, query, criteriaBuilder)
        spec.where.apply(froms, query, criteriaBuilder)
        spec.groupBy.apply(froms, query, criteriaBuilder)
        spec.having.apply(froms, query, criteriaBuilder)
        spec.orderBy.apply(froms, query, criteriaBuilder)

        return queryBuilder(query).apply {
            spec.limit.apply(this)
            spec.jpaHint.apply(this)
            spec.sqlHint.apply(this)
        }
    }

    fun <T, Q> createQuery(
        spec: CriteriaUpdateQuerySpec<T, Q>,
        criteriaBuilder: CriteriaBuilder,
        queryBuilder: (CriteriaUpdate<T>) -> Q
    ): Q {
        val createCriteriaUpdate = criteriaBuilder.createCriteriaUpdate(spec.targetEntity)
        val froms = spec.from.associate(spec.associate, createCriteriaUpdate)

        spec.where.apply(froms, createCriteriaUpdate, criteriaBuilder)
        spec.set.apply(froms, createCriteriaUpdate, criteriaBuilder)

        return queryBuilder(createCriteriaUpdate).apply {
            spec.jpaHint.apply(this)
            spec.sqlHint.apply(this)
        }
    }

    fun <T, Q> createQuery(
        spec: CriteriaDeleteQuerySpec<T, Q>,
        criteriaBuilder: CriteriaBuilder,
        queryBuilder: (CriteriaDelete<T>) -> Q
    ): Q {
        val createCriteriaDelete = criteriaBuilder.createCriteriaDelete(spec.targetEntity)
        val froms = spec.from.associate(spec.associate, createCriteriaDelete)

        spec.where.apply(froms, createCriteriaDelete, criteriaBuilder)

        return queryBuilder(createCriteriaDelete).apply {
            spec.jpaHint.apply(this)
            spec.sqlHint.apply(this)
        }
    }
}
