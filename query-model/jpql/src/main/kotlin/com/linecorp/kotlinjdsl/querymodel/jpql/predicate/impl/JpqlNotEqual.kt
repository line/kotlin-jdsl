package com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

data class JpqlNotEqual<T> internal constructor(
    val left: Expression<T>,
    val right: Expression<T>,
) : Predicate
