package com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate

@Internal
data class JpqlNotLike internal constructor(
    val value: Expression<String>,
    val pattern: Expression<String>,
    val escape: Expression<Char>?,
) : Predicate
