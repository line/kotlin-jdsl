package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

/**
 * A step to specify the target type for a cast from a string expression.
 * This corresponds to the BNF: CAST(string_expression AS {type})
 */
@SinceJdsl("3.6.0")
interface CastStep {
    /**
     * Casts the expression to an INTEGER.
     */
    @SinceJdsl("3.6.0")
    fun asInteger(): Expressionable<Int>

    /**
     * Casts the expression to a LONG.
     */
    @SinceJdsl("3.6.0")
    fun asLong(): Expressionable<Long>

    /**
     * Casts the expression to a FLOAT.
     */
    @SinceJdsl("3.6.0")
    fun asFloat(): Expressionable<Float>

    /**
     * Casts the expression to a DOUBLE.
     */
    @SinceJdsl("3.6.0")
    fun asDouble(): Expressionable<Double>
}
