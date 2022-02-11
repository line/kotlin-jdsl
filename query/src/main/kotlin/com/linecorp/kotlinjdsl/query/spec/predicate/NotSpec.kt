package com.linecorp.kotlinjdsl.query.spec.predicate

import com.linecorp.kotlinjdsl.query.spec.Froms
import javax.persistence.criteria.*

data class NotSpec(
    val predicate: PredicateSpec,
) : PredicateSpec {
    override fun toCriteriaPredicate(
        froms: Froms,
        query: AbstractQuery<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.not(predicate.toCriteriaPredicate(froms, query, criteriaBuilder))
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaUpdate<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.not(predicate.toCriteriaPredicate(froms, query, criteriaBuilder))
    }

    override fun toCriteriaPredicate(
        froms: Froms,
        query: CriteriaDelete<*>,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.not(predicate.toCriteriaPredicate(froms, query, criteriaBuilder))
    }
}
