package org.springframework.data.jpa.repository.query

import org.springframework.data.domain.Sort

internal object QueryUtilsAdaptor {
    fun applySorting(query: String, sort: Sort): String {
        return QueryUtils.applySorting(query, sort)
    }

    fun createCountQueryFor(originalQuery: String, countProjection: String?, nativeQuery: Boolean): String {
        return QueryUtils.createCountQueryFor(originalQuery, countProjection, nativeQuery)
    }
}
