package com.linecorp.kotlinjdsl.spring.data.querydsl.groupby

import com.linecorp.kotlinjdsl.query.clause.groupby.GroupByClause
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class SpringDataQueryDslImplGroupByTest : WithKotlinJdslAssertions {
    @Test
    fun noGroupBy() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.groupBy).isEqualTo(
            GroupByClause(emptyList())
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.groupBy).isEqualTo(
            GroupByClause(emptyList())
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.groupBy).isEqualTo(
            GroupByClause(emptyList())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.groupBy).isEqualTo(
            GroupByClause(emptyList())
        )
    }

    @Test
    fun groupByVararg() {
        // given
        val columnSpec1: ColumnSpec<String> = mockk()
        val columnSpec2: ColumnSpec<Int> = mockk()

        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            groupBy(columnSpec1, columnSpec2)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.groupBy).isEqualTo(
            GroupByClause(listOf(columnSpec1, columnSpec2))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.groupBy).isEqualTo(
            GroupByClause(listOf(columnSpec1, columnSpec2))
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.groupBy).isEqualTo(
            GroupByClause(emptyList())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.groupBy).isEqualTo(
            GroupByClause(emptyList())
        )
    }

    @Test
    fun groupByList() {
        // given
        val columnSpec1: ColumnSpec<String> = mockk()
        val columnSpec2: ColumnSpec<Int> = mockk()

        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            groupBy(listOf(columnSpec1, columnSpec2))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.groupBy).isEqualTo(
            GroupByClause(listOf(columnSpec1, columnSpec2))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.groupBy).isEqualTo(
            GroupByClause(listOf(columnSpec1, columnSpec2))
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.groupBy).isEqualTo(
            GroupByClause(emptyList())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.groupBy).isEqualTo(
            GroupByClause(emptyList())
        )
    }

    class Data1
}
