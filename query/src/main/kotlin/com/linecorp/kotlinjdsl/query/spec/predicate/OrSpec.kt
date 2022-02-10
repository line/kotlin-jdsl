package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.Predicate

data class OrSpec(
    val predicates: List<PredicateSpec?>,
) : PredicateSpec {

    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return toCriteriaPredicate(
            criteriaBuilder = criteriaBuilder,
            empty = { EmptyPredicateSpec.toCriteriaPredicate(froms, query, criteriaBuilder) }) {
            it.toCriteriaPredicate(
                froms,
                query,
                criteriaBuilder
            )
        }
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return toCriteriaPredicate(
            criteriaBuilder = criteriaBuilder,
            empty = { EmptyPredicateSpec.toCriteriaPredicate(froms, query, criteriaBuilder) }) {
            it.toCriteriaPredicate(
                froms,
                query,
                criteriaBuilder
            )
        }
    }

    fun toCriteriaPredicate(
        criteriaBuilder: CriteriaBuilder,
        empty: () -> Predicate,
        predicate: (PredicateSpec) -> Predicate
    ): Predicate {
        return predicates.asSequence()
            .filterNotNull()
            .map { predicate(it) }
            .reduceOrNull { left, right -> criteriaBuilder.or(left, right) }
            ?: empty()
    }
}
