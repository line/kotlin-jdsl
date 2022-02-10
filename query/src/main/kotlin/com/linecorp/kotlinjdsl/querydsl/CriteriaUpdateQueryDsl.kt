package com.linecorp.kotlinjdsl.querydsl

import com.linecorp.kotlinjdsl.querydsl.expression.ExpressionDsl
import com.linecorp.kotlinjdsl.querydsl.from.AssociateDsl
import com.linecorp.kotlinjdsl.querydsl.from.RelationDsl
import com.linecorp.kotlinjdsl.querydsl.hint.HintDsl
import com.linecorp.kotlinjdsl.querydsl.predicate.PredicateDsl
import com.linecorp.kotlinjdsl.querydsl.set.SetParameterDsl
import com.linecorp.kotlinjdsl.querydsl.where.WhereDsl

interface CriteriaUpdateQueryDsl :
    ExpressionDsl,
    PredicateDsl,
    AssociateDsl,
    RelationDsl,
    WhereDsl,
    HintDsl,
    SetParameterDsl
