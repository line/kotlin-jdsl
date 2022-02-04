package com.linecorp.kotlinjdsl.querydsl

import com.linecorp.kotlinjdsl.querydsl.expression.ExpressionDsl
import com.linecorp.kotlinjdsl.querydsl.from.FromDsl
import com.linecorp.kotlinjdsl.querydsl.from.JoinDsl
import com.linecorp.kotlinjdsl.querydsl.from.RelationDsl
import com.linecorp.kotlinjdsl.querydsl.groupby.GroupByDsl
import com.linecorp.kotlinjdsl.querydsl.having.HavingDsl
import com.linecorp.kotlinjdsl.querydsl.predicate.PredicateDsl
import com.linecorp.kotlinjdsl.querydsl.select.SingleSelectDsl
import com.linecorp.kotlinjdsl.querydsl.where.WhereDsl

interface SubqueryDsl<T> :
    ExpressionDsl,
    PredicateDsl,
    SingleSelectDsl<T>,
    FromDsl,
    JoinDsl,
    RelationDsl,
    WhereDsl,
    GroupByDsl,
    HavingDsl