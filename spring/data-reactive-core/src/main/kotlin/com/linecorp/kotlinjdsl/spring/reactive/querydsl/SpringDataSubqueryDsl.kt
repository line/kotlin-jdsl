package com.linecorp.kotlinjdsl.spring.reactive.querydsl

import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.predicate.SpringDataPredicateDsl

interface SpringDataSubqueryDsl<T> :
    SubqueryDsl<T>,
    SpringDataPredicateDsl
