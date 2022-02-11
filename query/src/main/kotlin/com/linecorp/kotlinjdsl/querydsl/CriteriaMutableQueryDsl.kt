package com.linecorp.kotlinjdsl.querydsl

import com.linecorp.kotlinjdsl.querydsl.expression.ExpressionDsl
import com.linecorp.kotlinjdsl.querydsl.from.AssociateDsl
import com.linecorp.kotlinjdsl.querydsl.from.RelationDsl
import com.linecorp.kotlinjdsl.querydsl.hint.HintDsl
import com.linecorp.kotlinjdsl.querydsl.predicate.PredicateDsl
import com.linecorp.kotlinjdsl.querydsl.where.WhereDsl

interface CriteriaMutableQueryDsl :
    ExpressionDsl,
    PredicateDsl,
    AssociateDsl,
    RelationDsl,
    WhereDsl,
    HintDsl

/**
 * According to the current specification, CriteriaMutableQueryDsl and CriteriaDeleteQueryDsl are the same.
 * so instead of creating an interface, create a typealias.
 * If the specifications change in the future, separate them into separate interfaces.
 * This is because CriteriaUpdateQueryDsl inherits from CriteriaMutableQueryDsl.
 */
typealias CriteriaDeleteQueryDsl = CriteriaMutableQueryDsl
