package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.CriteriaDeleteQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaUpdateQuerySpec
import com.linecorp.kotlinjdsl.query.ReactiveQuery

interface ReactiveCriteriaQueryCreator : JpaCriteriaQueryBuilder {
    fun <T> createQuery(spec: CriteriaQuerySpec<T, ReactiveQuery<T>>): ReactiveQuery<T>
    fun <T> createQuery(spec: CriteriaUpdateQuerySpec<T, ReactiveQuery<T>>): ReactiveQuery<T>
    fun <T> createQuery(spec: CriteriaDeleteQuerySpec<T, ReactiveQuery<T>>): ReactiveQuery<T>
}
