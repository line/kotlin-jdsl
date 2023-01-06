package com.linecorp.kotlinjdsl.hibernate.integration.querydsl.select

import com.linecorp.kotlinjdsl.test.integration.querydsl.select.AbstractCriteriaQueryDslSelectIntegrationTest
import org.junit.jupiter.api.Test
import java.lang.AssertionError

class HibernateCriteriaQueryDslSelectIntegrationTest : AbstractCriteriaQueryDslSelectIntegrationTest() {
    /**
     * Unlike hibernate 5, subquery in select is not properly supported in 6.
     * As the hibernate version upgrade progresses in the future,
     * we expect this test to break and operate normally.
     */
    @Test
    override fun `listQuery - subquery in select, subquery in from`() {
        catchThrowable(AssertionError::class) { super.`listQuery - subquery in select, subquery in from`() }
    }
}
