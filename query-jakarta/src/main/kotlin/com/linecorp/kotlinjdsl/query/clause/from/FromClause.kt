package com.linecorp.kotlinjdsl.query.clause.from

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import jakarta.persistence.criteria.AbstractQuery
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaDelete
import jakarta.persistence.criteria.CriteriaUpdate

/**
 * Internal Only
 * Don't use this directly because it's an **INTERNAL**.
 * It does not support backward compatibility.
 */
data class FromClause<T>(
    private val entity: EntitySpec<T>,
) {
    fun join(joinClause: JoinClause, query: AbstractQuery<*>, criteriaBuilder: CriteriaBuilder): Froms {
        return Joiner(entity, joinClause.joins, query, criteriaBuilder).joinAll()
    }

    fun associate(joinClause: SimpleAssociatedJoinClause, query: CriteriaUpdate<T>): Froms {
        return SimpleAssociator(entity, joinClause.joins, query.from(entity.type)).associateAll()
    }

    fun associate(joinClause: SimpleAssociatedJoinClause, query: CriteriaDelete<T>): Froms {
        return SimpleAssociator(entity, joinClause.joins, query.from(entity.type)).associateAll()
    }
}
