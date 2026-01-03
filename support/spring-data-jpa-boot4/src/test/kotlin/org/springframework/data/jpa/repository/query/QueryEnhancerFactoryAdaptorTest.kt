package org.springframework.data.jpa.repository.query

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class QueryEnhancerFactoryAdaptorTest {
    @Test
    fun forQuery() {
        val query = "SELECT e FROM Employee e"
        val enhancer = QueryEnhancerFactoryAdaptor.forQuery(query)
        assertThat(enhancer).isNotNull
    }
}
