package com.linecorp.kotlinjdsl.spring.data.querydsl

import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.spring.data.querydsl.predicate.SpringDataPredicateDsl

interface SpringDataSubqueryDsl<T> :
    SubqueryDsl<T>,
    SpringDataPredicateDsl
