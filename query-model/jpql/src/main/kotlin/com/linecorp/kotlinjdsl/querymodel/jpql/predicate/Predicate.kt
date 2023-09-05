package com.linecorp.kotlinjdsl.querymodel.jpql.predicate

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression

@SinceJdsl("3.0.0")
interface Predicate : Predicatable, Expression<Boolean>, QueryPart {
    override fun toPredicate(): Predicate = this
}
