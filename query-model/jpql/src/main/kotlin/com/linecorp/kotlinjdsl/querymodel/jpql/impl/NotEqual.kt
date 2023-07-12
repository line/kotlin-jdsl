package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicate

data class NotEqual<T>(
    val left: Expression<T>,
    val right: Expression<T>,
) : Predicate
