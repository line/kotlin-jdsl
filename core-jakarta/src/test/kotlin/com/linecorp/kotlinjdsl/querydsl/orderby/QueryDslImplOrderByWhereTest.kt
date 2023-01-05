package com.linecorp.kotlinjdsl.querydsl.orderby

import com.linecorp.kotlinjdsl.query.clause.orderby.OrderByClause
import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.query.spec.OrderSpec
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class QueryDslImplOrderByWhereTest : WithKotlinJdslAssertions {
    @Test
    fun noOrderBy() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.orderBy).isEqualTo(
            OrderByClause(emptyList())
        )
    }

    @Test
    fun orderByVararg() {
        // given
        val orderSpec1: OrderSpec = mockk()
        val orderSpec2: OrderSpec = mockk()

        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            orderBy(orderSpec1, orderSpec2)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.orderBy).isEqualTo(
            OrderByClause(listOf(orderSpec1, orderSpec2))
        )
    }

    @Test
    fun orderByList() {
        // given
        val orderSpec1: OrderSpec = mockk()
        val orderSpec2: OrderSpec = mockk()

        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            orderBy(listOf(orderSpec1, orderSpec2))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.orderBy).isEqualTo(
            OrderByClause(listOf(orderSpec1, orderSpec2))
        )
    }

    @Test
    fun asc() {
        val expressionSpec: ExpressionSpec<String> = mockk()

        val actual: OrderSpec

        QueryDslImpl(Data1::class.java).apply {
            actual = expressionSpec.asc()
        }

        assertThat(actual).isEqualTo(ExpressionOrderSpec(expressionSpec, true))
    }

    @Test
    fun desc() {
        val expressionSpec: ExpressionSpec<String> = mockk()

        val actual: OrderSpec

        QueryDslImpl(Data1::class.java).apply {
            actual = expressionSpec.desc()
        }

        assertThat(actual).isEqualTo(ExpressionOrderSpec(expressionSpec, false))
    }

    class Data1
}
