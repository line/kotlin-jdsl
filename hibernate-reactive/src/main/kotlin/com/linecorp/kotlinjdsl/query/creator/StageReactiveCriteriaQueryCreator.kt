package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.*
import org.hibernate.reactive.stage.Stage
import javax.persistence.criteria.CriteriaBuilder

class StageReactiveCriteriaQueryCreator(
    private val criteriaBuilder: CriteriaBuilder, private val session: Stage.Session
) : ReactiveCriteriaQueryCreator {
    override fun <T> createQuery(spec: CriteriaQuerySpec<T, ReactiveQuery<T>>): ReactiveQuery<T> =
        createQuery(spec = spec, criteriaBuilder = criteriaBuilder) {
            HibernateStageReactiveQuery(session.createQuery(it))
        }

    override fun <T> createQuery(spec: CriteriaUpdateQuerySpec<T, ReactiveQuery<T>>) =
        createQuery(spec = spec, criteriaBuilder = criteriaBuilder) {
            HibernateStageReactiveQuery(session.createQuery(it))
        }

    override fun <T> createQuery(spec: CriteriaDeleteQuerySpec<T, ReactiveQuery<T>>) =
        createQuery(spec = spec, criteriaBuilder = criteriaBuilder) {
            HibernateStageReactiveQuery(session.createQuery(it))
        }
}
