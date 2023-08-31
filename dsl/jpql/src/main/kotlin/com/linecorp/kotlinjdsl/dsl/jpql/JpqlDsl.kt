package com.linecorp.kotlinjdsl.dsl.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.dsl.jpql.JpqlDsl.Constructor

/**
 * Marker interface to indicate that this is DSL for JPQL.
 *
 * The class that implements this interface must have the companion object that implements [Constructor].
 */
@SinceJdsl("3.0.0")
interface JpqlDsl {
    /**
     * Constructor to help avoid initializing DSL.
     *
     * @see jpql
     */
    @SinceJdsl("3.0.0")
    interface Constructor<T : JpqlDsl> {
        fun newInstance(): T
    }
}
