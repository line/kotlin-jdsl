package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import jakarta.persistence.criteria.*

data class AndSpec(
    private val predicates: List<PredicateSpec?>,
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return toCriteriaPredicate(criteriaBuilder) { it.toCriteriaPredicate(froms, query, criteriaBuilder) }
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return toCriteriaPredicate(criteriaBuilder) { it.toCriteriaPredicate(froms, query, criteriaBuilder) }
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return toCriteriaPredicate(criteriaBuilder) { it.toCriteriaPredicate(froms, query, criteriaBuilder) }
    }

    private fun toCriteriaPredicate(
        criteriaBuilder: CriteriaBuilder,
        predicate: (PredicateSpec) -> Predicate
    ): Predicate {
        return predicates.asSequence()
            .filterNotNull()
            .map { predicate(it) }
            .reduceOrNull { left, right -> criteriaBuilder.and(left, right) }
            ?: criteriaBuilder.conjunction()
    }
}
