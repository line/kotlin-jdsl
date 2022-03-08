package com.linecorp.kotlinjdsl.spring.reactive.querydsl

import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.predicate.SpringDataPredicateDsl

interface SpringDataCriteriaUpdateQueryDsl :
    CriteriaUpdateQueryDsl,
    SpringDataPredicateDsl
