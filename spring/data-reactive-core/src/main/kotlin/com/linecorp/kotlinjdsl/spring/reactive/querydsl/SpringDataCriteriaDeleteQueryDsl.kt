package com.linecorp.kotlinjdsl.spring.reactive.querydsl

import com.linecorp.kotlinjdsl.querydsl.CriteriaDeleteQueryDsl
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.predicate.SpringDataPredicateDsl

interface SpringDataCriteriaDeleteQueryDsl : CriteriaDeleteQueryDsl, SpringDataPredicateDsl
