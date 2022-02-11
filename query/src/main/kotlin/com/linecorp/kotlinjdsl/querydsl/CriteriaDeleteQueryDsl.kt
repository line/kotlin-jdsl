package com.linecorp.kotlinjdsl.querydsl

import com.linecorp.kotlinjdsl.querydsl.from.AssociateDsl
import com.linecorp.kotlinjdsl.querydsl.from.RelationDsl
import com.linecorp.kotlinjdsl.querydsl.hint.HintDsl
import com.linecorp.kotlinjdsl.querydsl.where.WhereDsl

interface CriteriaDeleteQueryDsl :
    AssociateDsl,
    RelationDsl,
    WhereDsl,
    HintDsl
