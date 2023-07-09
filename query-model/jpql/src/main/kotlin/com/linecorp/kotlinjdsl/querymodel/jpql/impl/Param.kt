package com.linecorp.kotlinjdsl.querymodel.jpql.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.Expression

data class Param<T>(
    val name: String?,
    val value: T?,
) : Expression<T>
