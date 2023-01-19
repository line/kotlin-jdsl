package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.*
import org.hibernate.reactive.mutiny.Mutiny
import jakarta.persistence.criteria.CriteriaBuilder

class MutinyStatelessReactiveCriteriaQueryCreator(
    private val criteriaBuilder: CriteriaBuilder, private val session: Mutiny.StatelessSession
) : ReactiveCriteriaQueryCreator {
    override fun <T> createQuery(spec: CriteriaQuerySpec<T, ReactiveQuery<T>>): ReactiveQuery<T> =
        JpaCriteriaQueryBuilder.createQuery(spec = spec, criteriaBuilder = criteriaBuilder) {
            HibernateMutinyReactiveQuery(session.createQuery(it))
        }

    override fun <T> createQuery(spec: CriteriaUpdateQuerySpec<T, ReactiveQuery<T>>) =
        JpaCriteriaQueryBuilder.createQuery(spec = spec, criteriaBuilder = criteriaBuilder) {
            HibernateMutinyReactiveQuery(session.createQuery(it))
        }

    override fun <T> createQuery(spec: CriteriaDeleteQuerySpec<T, ReactiveQuery<T>>) =
        JpaCriteriaQueryBuilder.createQuery(spec = spec, criteriaBuilder = criteriaBuilder) {
            HibernateMutinyReactiveQuery(session.createQuery(it))
        }
}
