package com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@Internal
data class JpqlNotBetween internal constructor(
    val value: Expression<*>,
    val min: Expression<*>,
    val max: Expression<*>,
) : Predicate
