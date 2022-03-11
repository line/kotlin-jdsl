package com.linecorp.kotlinjdsl.spring.reactive.querydsl

import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.predicate.SpringDataReactivePredicateDsl

interface SpringDataReactiveSubqueryDsl<T> :
    SubqueryDsl<T>,
    SpringDataReactivePredicateDsl
