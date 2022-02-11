package com.linecorp.kotlinjdsl.spring.data.querydsl

import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import com.linecorp.kotlinjdsl.spring.data.querydsl.predicate.SpringDataPredicateDsl

interface SpringDataCriteriaUpdateQueryDsl :
    CriteriaUpdateQueryDsl,
    SpringDataPredicateDsl
