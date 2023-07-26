package com.linecorp.kotlinjdsl.dsl.jpql.entity

import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joinable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

interface JoinOnStep : Joinable {
    fun on(predicate: Predicate): JoinOnStep
}
