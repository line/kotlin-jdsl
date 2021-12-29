package com.linecorp.kotlinjdsl.querydsl.where

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec

interface WhereDsl {
    fun where(predicate: PredicateSpec)
}