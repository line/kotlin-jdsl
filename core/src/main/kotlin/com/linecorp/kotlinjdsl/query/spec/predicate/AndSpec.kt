package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate

data class AndSpec(
    val predicates: List<PredicateSpec?>,
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return predicates.asSequence()
            .filterNotNull()
            .map { it.toCriteriaPredicate(froms, query, criteriaBuilder) }
            .reduceOrNull { left, right -> criteriaBuilder.and(left, right) }
            ?: criteriaBuilder.conjunction()
    }
}