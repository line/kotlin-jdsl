package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.Predicate

interface PredicateSpec {
    companion object {
        val empty = EmptyPredicateSpec
    }

    fun isEmpty(): Boolean = this === empty

    fun reverse() = NotSpec(this)

    infix fun and(other: PredicateSpec) = AndSpec(listOf(this, other))
    infix fun or(other: PredicateSpec) = OrSpec(listOf(this, other))

    fun toCriteriaPredicate(froms: Froms, query: AbstractQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate
    fun toCriteriaPredicate(froms: Froms, query: CriteriaUpdate<*>, criteriaBuilder: CriteriaBuilder): Predicate
}
