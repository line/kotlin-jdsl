package com.linecorp.kotlinjdsl.spring.data.querydsl

import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.spring.data.querydsl.predicate.SpringDataPredicateDsl

interface SpringDataCriteriaQueryDsl<T> :
    CriteriaQueryDsl<T>,
    SpringDataPredicateDsl
