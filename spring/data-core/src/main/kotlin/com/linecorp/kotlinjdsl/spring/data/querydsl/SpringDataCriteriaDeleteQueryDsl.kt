package com.linecorp.kotlinjdsl.spring.data.querydsl

import com.linecorp.kotlinjdsl.querydsl.CriteriaDeleteQueryDsl
import com.linecorp.kotlinjdsl.spring.data.querydsl.predicate.SpringDataPredicateDsl

interface SpringDataCriteriaDeleteQueryDsl : CriteriaDeleteQueryDsl, SpringDataPredicateDsl
