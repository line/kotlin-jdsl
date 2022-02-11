package com.linecorp.kotlinjdsl.query

import com.linecorp.kotlinjdsl.query.clause.set.SetClause

interface CriteriaUpdateQuerySpec<T> : CriteriaMutableQuerySpec<T> {
    val set: SetClause
}
