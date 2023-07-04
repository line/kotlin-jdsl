package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate

@Internal
data class Not(
    val predicate: Predicate,
) : Predicate
