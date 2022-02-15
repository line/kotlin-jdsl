package com.linecorp.kotlinjdsl.querydsl.from

import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test

internal class QueryDslImplFromTest : WithKotlinJdslAssertions {
    @Test
    fun fromJavaClass() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.from).isEqualTo(
            FromClause(EntitySpec(Data1::class.java))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.from).isEqualTo(
            FromClause(EntitySpec(Data1::class.java))
        )
    }

    @Test
    fun fromKotlinClass() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.from).isEqualTo(
            FromClause(EntitySpec(Data1::class.java))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.from).isEqualTo(
            FromClause(EntitySpec(Data1::class.java))
        )
    }

    @Test
    fun fromEntitySpec() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class, "data1"))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.from).isEqualTo(
            FromClause(EntitySpec(Data1::class.java, "data1"))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.from).isEqualTo(
            FromClause(EntitySpec(Data1::class.java, "data1"))
        )
    }

    class Data1
}
