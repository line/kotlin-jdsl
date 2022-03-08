package com.linecorp.kotlinjdsl.spring.reactive.querydsl

import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.predicate.SpringDataPredicateDsl

interface SpringDataCriteriaQueryDsl<T> :
    CriteriaQueryDsl<T>,
    SpringDataPredicateDsl
