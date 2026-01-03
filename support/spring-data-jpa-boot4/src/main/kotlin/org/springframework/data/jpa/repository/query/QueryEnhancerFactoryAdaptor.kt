package org.springframework.data.jpa.repository.query

import org.springframework.data.jpa.repository.query.DeclaredQuery
import org.springframework.data.jpa.repository.query.QueryEnhancer
import org.springframework.data.jpa.repository.query.QueryEnhancerFactories

internal object QueryEnhancerFactoryAdaptor {
    fun forQuery(query: String): QueryEnhancer = QueryEnhancerFactories.jpql().create(DeclaredQuery.jpqlQuery(query))
}
