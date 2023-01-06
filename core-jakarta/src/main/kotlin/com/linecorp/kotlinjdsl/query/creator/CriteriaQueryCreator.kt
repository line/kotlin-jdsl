package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.CriteriaDeleteQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaUpdateQuerySpec
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery

interface CriteriaQueryCreator {
    fun <T> createQuery(spec: CriteriaQuerySpec<T, TypedQuery<T>>): TypedQuery<T>
    fun <T> createQuery(spec: CriteriaUpdateQuerySpec<T, Query>): Query
    fun <T> createQuery(spec: CriteriaDeleteQuerySpec<T, Query>): Query
}
