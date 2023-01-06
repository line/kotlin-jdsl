package com.linecorp.kotlinjdsl.querydsl.having

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec

interface HavingDsl {
    fun having(predicate: PredicateSpec)
}
