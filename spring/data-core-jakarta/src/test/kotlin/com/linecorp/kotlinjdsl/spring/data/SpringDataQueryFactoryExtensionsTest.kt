package com.linecorp.kotlinjdsl.spring.data

import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.querydsl.*
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.order.Order
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.util.stream.Stream
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery

@ExtendWith(MockKExtension::class)
internal class SpringDataQueryFactoryExtensionsTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var queryFactory: SpringDataQueryFactory

    @MockK
    private lateinit var typedQuery: TypedQuery<Data1>

    @MockK
    private lateinit var subqueryExpressionSpec: SubqueryExpressionSpec<Data1>

    @MockK
    private lateinit var page: Page<Data1>

    @Test
    fun singleQuery() {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns typedQuery
        every { typedQuery.singleResult } returns Data1()

        val dsl: SpringDataCriteriaQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual: Data1 = queryFactory.singleQuery(dsl)

        // then
        assertThat(actual).isEqualTo(Data1())

        verify(exactly = 1) {
            queryFactory.selectQuery(Data1::class.java, dsl)
            typedQuery.singleResult
        }

        confirmVerified(queryFactory, typedQuery)
    }

    @Test
    fun listQuery() {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns typedQuery
        every { typedQuery.resultList } returns listOf(Data1())

        val dsl: SpringDataCriteriaQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual: List<Data1> = queryFactory.listQuery(dsl)

        // then
        assertThat(actual).isEqualTo(listOf(Data1()))

        verify(exactly = 1) {
            queryFactory.selectQuery(Data1::class.java, dsl)
            typedQuery.resultList
        }

        confirmVerified(queryFactory, typedQuery)
    }

    @Test
    fun selectQuery() {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns typedQuery

        val dsl: SpringDataCriteriaQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual: TypedQuery<Data1> = queryFactory.selectQuery(dsl)

        // then
        assertThat(actual).isEqualTo(typedQuery)

        verify(exactly = 1) {
            queryFactory.selectQuery(Data1::class.java, dsl)
        }

        confirmVerified(queryFactory)
    }

    @Test
    fun updateQuery() {
        // given
        every { queryFactory.updateQuery<Data1>(any(), any()) } returns typedQuery

        val dsl: SpringDataCriteriaUpdateQueryDsl.() -> Unit = {
            set(col(Data1::id), 1)
            where(col(Data1::id).equal(2))
        }

        // when
        val actual: Query = queryFactory.updateQuery<Data1>(dsl)

        // then
        assertThat(actual).isEqualTo(typedQuery)

        verify(exactly = 1) {
            queryFactory.updateQuery(Data1::class, dsl)
        }

        confirmVerified(queryFactory)
    }

    @Test
    fun deleteQuery() {
        // given
        every { queryFactory.deleteQuery<Data1>(any(), any()) } returns typedQuery

        val dsl: SpringDataCriteriaDeleteQueryDsl.() -> Unit = {
            where(col(Data1::id).equal(1))
        }

        // when
        val actual: Query = queryFactory.deleteQuery<Data1>(dsl)

        // then
        assertThat(actual).isEqualTo(typedQuery)

        verify(exactly = 1) {
            queryFactory.deleteQuery(Data1::class, dsl)
        }

        confirmVerified(queryFactory)
    }

    @Test
    fun subquery() {
        // given
        every { queryFactory.subquery<Data1>(any(), any()) } returns subqueryExpressionSpec

        val dsl: SpringDataSubqueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual: SubqueryExpressionSpec<Data1> = queryFactory.subquery(dsl)

        // then
        assertThat(actual).isEqualTo(subqueryExpressionSpec)

        verify(exactly = 1) {
            queryFactory.subquery(Data1::class.java, dsl)
        }

        confirmVerified(queryFactory)
    }

    @Test
    fun pageQuery() {
        // given
        every { queryFactory.pageQuery<Data1>(any(), any(), any()) } returns page

        val pageable = PageRequest.of(1, 10)

        val dsl: SpringDataPageableQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual: Page<Data1> = queryFactory.pageQuery(pageable, dsl)

        // then
        assertThat(actual).isEqualTo(page)

        verify(exactly = 1) {
            queryFactory.pageQuery(Data1::class.java, pageable, dsl)
        }

        confirmVerified(queryFactory)
    }

    @Test
    fun streamQuery() {
        // given
        every { queryFactory.streamQuery<Long>(any()) } returns Stream.of(1L, 2L, 3L)

        // when
        val actual = queryFactory.streamQuery<Long> {
            select(col(Order::id))
            from(entity(Order::class))
            where(col(Order::purchaserId).equal(1000))
        }.toList()

        // then
        assertThat(actual).containsExactly(1L, 2L, 3L)
    }

    @Test
    fun `pageQuery with countProjection`() {
        // given
        every { queryFactory.pageQuery<Data1>(any(), any(), any(), any()) } returns page

        val pageable = PageRequest.of(1, 10)

        val dsl: SpringDataPageableQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        val countProjection: SpringDataPageableQueryDsl<Long>.() -> SingleSelectClause<Long> = {
            select(count(column(Data1::id)))
        }

        // when
        val actual: Page<Data1> = queryFactory.pageQuery(pageable, dsl, countProjection)

        // then
        assertThat(actual).isEqualTo(page)

        verify(exactly = 1) {
            queryFactory.pageQuery(Data1::class.java, pageable, dsl, countProjection)
        }

        confirmVerified(queryFactory)
    }

    data class Data1(
        val id: Int = 100
    )
}
