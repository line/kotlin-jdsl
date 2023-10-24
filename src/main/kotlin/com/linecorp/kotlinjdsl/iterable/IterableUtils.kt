package com.linecorp.kotlinjdsl.iterable

object IterableUtils {
    fun isEmpty(iterable: Iterable<*>): Boolean {
        return if (iterable is Collection) {
            iterable.size == 0
        } else {
            !iterable.iterator().hasNext()
        }
    }

    fun isNotEmpty(iterable: Iterable<*>): Boolean {
        return !isEmpty(iterable)
    }
}
