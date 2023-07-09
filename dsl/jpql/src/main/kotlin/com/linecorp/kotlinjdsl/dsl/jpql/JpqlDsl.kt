package com.linecorp.kotlinjdsl.dsl.jpql

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
interface JpqlDsl {
    interface Constructor<T : JpqlDsl> {
        fun newInstance(): T
    }
}
