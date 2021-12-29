package com.linecorp.kotlinjdsl.query.clause.from

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import javax.persistence.criteria.AbstractQuery

data class FromClause(
    val entity: EntitySpec<*>,
) {
    fun join(joinClause: JoinClause, query: AbstractQuery<*>): Froms {
        return Joiner(entity, joinClause.joins, query).joinAll()
    }
}