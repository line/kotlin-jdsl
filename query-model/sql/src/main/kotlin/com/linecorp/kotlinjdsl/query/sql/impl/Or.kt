package com.linecorp.kotlinjdsl.query.sql.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.query.sql.Predicate

@Internal
data class Or(
    val predicates: Collection<Predicate>,
) : Predicate {
}
