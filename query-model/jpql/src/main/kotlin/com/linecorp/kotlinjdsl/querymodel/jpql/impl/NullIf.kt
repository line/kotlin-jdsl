package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression

data class NullIf<T>(
    val left: Expression<T>,
    val right: Expression<T>,
) : Expression<T?>
