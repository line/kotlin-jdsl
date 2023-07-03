package com.linecorp.kotlinjdsl.dsl.sql

import com.linecorp.kotlinjdsl.SinceJdsl

@SinceJdsl("3.0.0")
interface SqlDsl {
    interface Constructor<T : SqlDsl> {
        fun newInstance(): T
    }
}
