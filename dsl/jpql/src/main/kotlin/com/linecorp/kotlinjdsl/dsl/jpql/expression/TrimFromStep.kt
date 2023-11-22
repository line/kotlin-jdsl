package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable

@SinceJdsl("3.1.0")
interface TrimFromStep {
    /**
     * Creates a from in a trim expression.
     */
    @SinceJdsl("3.1.0")
    fun from(value: String): Expressionable<String>

    /**
     * Creates a from in a trim expression.
     */
    @SinceJdsl("3.1.0")
    fun from(value: Expressionable<String>): Expressionable<String>
}
