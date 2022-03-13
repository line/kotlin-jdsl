package com.linecorp.kotlinjdsl.query

inline fun <reified T : Any, R> ReactiveQuery<R>.unwrap(): T = unwrap(T::class)
