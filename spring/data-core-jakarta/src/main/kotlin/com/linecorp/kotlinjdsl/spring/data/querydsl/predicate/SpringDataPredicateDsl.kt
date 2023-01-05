@file:Suppress("RemoveExplicitTypeArguments")

package com.linecorp.kotlinjdsl.spring.data.querydsl.predicate

import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.AndSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.GreaterThanValueSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.LessThanValueSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.predicate.PredicateDsl
import org.springframework.data.domain.Range

interface SpringDataPredicateDsl : PredicateDsl {
    fun <T, R> ExpressionSpec<T>.greaterThan(bound: Range.Bound<R>) where R : Comparable<R>, R : Any, T : R? =
        if (bound.isBounded) {
            GreaterThanValueSpec<T, R>(this, bound.value.get(), bound.isInclusive)
        } else {
            PredicateSpec.empty
        }

    fun <T, R> ExpressionSpec<T>.lessThan(bound: Range.Bound<R>) where R : Comparable<R>, R : Any, T : R? =
        if (bound.isBounded) {
            LessThanValueSpec<T, R>(this, bound.value.get(), bound.isInclusive)
        } else {
            PredicateSpec.empty
        }

    fun <T, R> ExpressionSpec<T>.between(range: Range<R>) where R : Comparable<R>, R : Any, T : R? =
        AndSpec(listOf(greaterThan(range.lowerBound), lessThan(range.upperBound)))
}
