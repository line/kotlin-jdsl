package com.linecorp.kotlinjdsl.querydsl

import com.linecorp.kotlinjdsl.querydsl.expression.ExpressionDsl
import com.linecorp.kotlinjdsl.querydsl.from.*
import com.linecorp.kotlinjdsl.querydsl.groupby.GroupByDsl
import com.linecorp.kotlinjdsl.querydsl.having.HavingDsl
import com.linecorp.kotlinjdsl.querydsl.hint.HintDsl
import com.linecorp.kotlinjdsl.querydsl.limit.LimitDsl
import com.linecorp.kotlinjdsl.querydsl.orderby.OrderByDsl
import com.linecorp.kotlinjdsl.querydsl.predicate.PredicateDsl
import com.linecorp.kotlinjdsl.querydsl.select.MultiSelectDsl
import com.linecorp.kotlinjdsl.querydsl.select.SingleSelectDsl
import com.linecorp.kotlinjdsl.querydsl.where.WhereDsl

interface CriteriaQueryDsl<T> :
    ExpressionDsl,
    PredicateDsl,
    SingleSelectDsl<T>,
    MultiSelectDsl<T>,
    FromDsl,
    JoinDsl,
    AssociateDsl,
    FetchDsl,
    RelationDsl,
    WhereDsl,
    GroupByDsl,
    HavingDsl,
    OrderByDsl,
    LimitDsl,
    HintDsl
