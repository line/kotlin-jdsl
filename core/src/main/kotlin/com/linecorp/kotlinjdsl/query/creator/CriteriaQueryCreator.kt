package com.linecorp.kotlinjdsl.query.creator

import com.linecorp.kotlinjdsl.query.CriteriaQuerySpec
import javax.persistence.TypedQuery

interface CriteriaQueryCreator {
    fun <T> createQuery(spec: CriteriaQuerySpec<T>): TypedQuery<T>
}
