package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

/**
 * A step to cast a scalar expression to a STRING.
 * This corresponds to the BNF: CAST(scalar_expression AS STRING)
 */
@SinceJdsl("3.6.0")
interface CastStepToString {
    /**
     * Casts the expression to a STRING.
     */
    @SinceJdsl("3.6.0")
    fun asString(): Expressionable<String>
}
