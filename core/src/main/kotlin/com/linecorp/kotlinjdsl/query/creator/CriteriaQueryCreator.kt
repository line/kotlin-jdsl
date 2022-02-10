package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import com.linecorp.kotlinjdsl.query.CriteriaUpdateQuerySpec
import javax.persistence.Query
import javax.persistence.TypedQuery

interface CriteriaQueryCreator {
    fun <T> createQuery(spec: CriteriaQuerySpec<T>): TypedQuery<T>
    fun <T> createQuery(spec: CriteriaUpdateQuerySpec<T>): Query
}
