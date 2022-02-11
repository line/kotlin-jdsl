package com.linecorp.kotlinjdsl.spring.data.querydsl

import com.linecorp.kotlinjdsl.querydsl.CriteriaMutableQueryDsl
import com.linecorp.kotlinjdsl.spring.data.querydsl.predicate.SpringDataPredicateDsl

interface SpringDataCriteriaMutableQueryDsl :
    CriteriaMutableQueryDsl,
    SpringDataPredicateDsl

typealias SpringDataCriteriaDeleteQueryDsl = SpringDataCriteriaMutableQueryDsl
