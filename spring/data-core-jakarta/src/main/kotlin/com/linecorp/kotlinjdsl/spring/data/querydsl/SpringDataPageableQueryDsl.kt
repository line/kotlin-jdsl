package com.linecorp.kotlinjdsl.spring.data.querydsl

import com.linecorp.kotlinjdsl.querydsl.expression.ExpressionDsl
import com.linecorp.kotlinjdsl.querydsl.from.FromDsl
import com.linecorp.kotlinjdsl.querydsl.from.JoinDsl
import com.linecorp.kotlinjdsl.querydsl.from.RelationDsl
import com.linecorp.kotlinjdsl.querydsl.hint.HintDsl
import com.linecorp.kotlinjdsl.querydsl.select.MultiSelectDsl
import com.linecorp.kotlinjdsl.querydsl.select.SingleSelectDsl
import com.linecorp.kotlinjdsl.querydsl.where.WhereDsl
import com.linecorp.kotlinjdsl.spring.data.querydsl.predicate.SpringDataPredicateDsl

/**
 * DSL for Spring Data JPA Pageable
 *
 * Pageable DSL does not support Fetch and GroupBy clause because they have to load a lot of data to memory.
 * To use the Fetch and GroupBy clause for pagination, separate the query.
 */
interface SpringDataPageableQueryDsl<T> :
    ExpressionDsl,
    SpringDataPredicateDsl,
    SingleSelectDsl<T>,
    MultiSelectDsl<T>,
    FromDsl,
    JoinDsl,
    RelationDsl,
    WhereDsl,
    HintDsl
