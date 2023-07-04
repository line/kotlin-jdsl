package com.linecorp.kotlinjdsl.querymodel.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.sql.Predicate

@Internal
data class Or(
    val predicates: Collection<Predicate>,
) : Predicate {
}
