package com.linecorp.kotlinjdsl.spring.data.reactive.querydsl.orderby

import com.linecorp.kotlinjdsl.query.clause.orderby.OrderByClause
import com.linecorp.kotlinjdsl.query.spec.ExpressionOrderSpec
import com.linecorp.kotlinjdsl.query.spec.OrderSpec
import com.linecorp.kotlinjdsl.query.spec.expression.ExpressionSpec
import com.linecorp.kotlinjdsl.spring.reactive.query.clause.orderby.SpringDataReactivePageableOrderByClause
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.SpringDataReactiveReactiveQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

internal class SpringDataReactiveQueryDslImplOrderByWhereTest : WithKotlinJdslAssertions {
    @Test
    fun noOrderBy() {
        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.orderBy).isEqualTo(
            OrderByClause(emptyList())
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.orderBy).isEqualTo(
            SpringDataReactivePageableOrderByClause(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.orderBy).isEqualTo(
            OrderByClause(emptyList())
        )
    }

    @Test
    fun orderByVararg() {
        // given
        val orderSpec1: OrderSpec = mockk()
        val orderSpec2: OrderSpec = mockk()

        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            orderBy(orderSpec1, orderSpec2)
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.orderBy).isEqualTo(
            OrderByClause(listOf(orderSpec1, orderSpec2))
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.orderBy).isEqualTo(
            SpringDataReactivePageableOrderByClause(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.orderBy).isEqualTo(
            OrderByClause(emptyList())
        )
    }

    @Test
    fun orderByList() {
        // given
        val orderSpec1: OrderSpec = mockk()
        val orderSpec2: OrderSpec = mockk()

        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            orderBy(listOf(orderSpec1, orderSpec2))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.orderBy).isEqualTo(
            OrderByClause(listOf(orderSpec1, orderSpec2))
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.orderBy).isEqualTo(
            SpringDataReactivePageableOrderByClause(Pageable.unpaged())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.orderBy).isEqualTo(
            OrderByClause(emptyList())
        )
    }

    @Test
    fun pageable() {
        // given
        val pageable = PageRequest.of(10, 10)

        // when
        val actual = SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            this.pageable = pageable
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.orderBy).isEqualTo(
            OrderByClause(emptyList())
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.orderBy).isEqualTo(
            SpringDataReactivePageableOrderByClause(pageable)
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.orderBy).isEqualTo(
            OrderByClause(emptyList())
        )
    }

    @Test
    fun asc() {
        val expressionSpec: ExpressionSpec<String> = mockk()

        val actual: OrderSpec

        SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            actual = expressionSpec.asc()
        }

        assertThat(actual).isEqualTo(ExpressionOrderSpec(expressionSpec, true))
    }

    @Test
    fun desc() {
        val expressionSpec: ExpressionSpec<String> = mockk()

        val actual: OrderSpec

        SpringDataReactiveReactiveQueryDslImpl(Data1::class.java).apply {
            actual = expressionSpec.desc()
        }

        assertThat(actual).isEqualTo(ExpressionOrderSpec(expressionSpec, false))
    }

    class Data1
}
