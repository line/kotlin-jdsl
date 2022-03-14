package com.linecorp.kotlinjdsl.spring.reactive.querydsl

import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.predicate.SpringDataReactivePredicateDsl

interface SpringDataReactiveCriteriaQueryDsl<T> :
    CriteriaQueryDsl<T>,
    SpringDataReactivePredicateDsl
