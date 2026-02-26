package org.springframework.data.jpa.repository.query

internal object QueryEnhancerFactoryAdaptor {
    fun forQuery(query: String): QueryEnhancer = QueryEnhancerFactories.jpql().create(DeclaredQuery.jpqlQuery(query))
}
