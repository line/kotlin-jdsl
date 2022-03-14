package com.linecorp.kotlinjdsl.querydsl.expression

import com.linecorp.kotlinjdsl.query.spec.expression.CaseSpec
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec

data class WhenDsl(
    private val `when`: PredicateSpec,
) {
    infix fun <T> then(expression: ExpressionSpec<out T>) = CaseSpec.WhenSpec(`when`, expression)
    infix fun <T> then(expression: () -> ExpressionSpec<out T>) = CaseSpec.WhenSpec(`when`, expression())
}
