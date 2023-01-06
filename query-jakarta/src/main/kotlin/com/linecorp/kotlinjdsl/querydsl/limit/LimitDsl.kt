package com.linecorp.kotlinjdsl.querydsl.limit

interface LimitDsl {
    fun offset(offset: Int)
    fun maxResults(maxResults: Int)

    fun limit(maxResults: Int) {
        maxResults(maxResults)
    }

    fun limit(offset: Int, maxResults: Int) {
        offset(offset)
        maxResults(maxResults)
    }
}
