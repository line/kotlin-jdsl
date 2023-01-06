package com.linecorp.kotlinjdsl.querydsl.where

import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.expression.ExpressionDsl
import com.linecorp.kotlinjdsl.querydsl.predicate.PredicateDsl

interface WhereDsl : ExpressionDsl, PredicateDsl {
    fun where(predicate: PredicateSpec?)

    fun whereAnd(vararg predicates: PredicateSpec?) = whereAnd(predicates.toList())
    fun whereAnd(predicates: List<PredicateSpec?>)

    fun whereOr(vararg predicates: PredicateSpec?) = whereOr(predicates.toList())
    fun whereOr(predicates: List<PredicateSpec?>)
}
