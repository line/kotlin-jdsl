package com.linecorp.kotlinjdsl.spring.data.reactive

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.reactive.*
import com.linecorp.kotlinjdsl.spring.reactive.querydsl.*
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import java.util.concurrent.CompletableFuture

@ExtendWith(MockKExtension::class)
internal class SpringDataReactiveQueryFactoryExtensionsTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var queryFactory: SpringDataReactiveQueryFactory

    @MockK
    private lateinit var reactiveQuery: ReactiveQuery<Data1>

    @MockK
    private lateinit var subqueryExpressionSpec: SubqueryExpressionSpec<Data1>

    @MockK
    private lateinit var page: Page<Data1>

    @Test
    fun singleQuery(): Unit = runBlocking {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns reactiveQuery
        every { reactiveQuery.singleResult } returns CompletableFuture.completedFuture(Data1())

        val dsl: SpringDataCriteriaQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual: Data1 = queryFactory.singleQuery(dsl).await()

        // then
        assertThat(actual).isEqualTo(Data1())

        verify(exactly = 1) {
            queryFactory.selectQuery(Data1::class.java, dsl)
            reactiveQuery.singleResult
        }

        confirmVerified(queryFactory, reactiveQuery)
    }

    @Test
    fun listQuery(): Unit = runBlocking {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns reactiveQuery
        every { reactiveQuery.resultList } returns CompletableFuture.completedFuture(listOf(Data1()))

        val dsl: SpringDataCriteriaQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual: List<Data1> = queryFactory.listQuery(dsl).await()

        // then
        assertThat(actual).isEqualTo(listOf(Data1()))

        verify(exactly = 1) {
            queryFactory.selectQuery(Data1::class.java, dsl)
            reactiveQuery.resultList
        }

        confirmVerified(queryFactory, reactiveQuery)
    }

    @Test
    fun selectQuery(): Unit = runBlocking {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns reactiveQuery

        val dsl: SpringDataCriteriaQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual = queryFactory.selectQuery(dsl)

        // then
        assertThat(actual).isEqualTo(reactiveQuery)

        verify(exactly = 1) {
            queryFactory.selectQuery(Data1::class.java, dsl)
        }

        confirmVerified(queryFactory)
    }

    @Test
    fun updateQuery() {
        // given
        every { queryFactory.updateQuery<Data1>(any(), any()) } returns reactiveQuery

        val dsl: SpringDataCriteriaUpdateQueryDsl.() -> Unit = {
            set(col(Data1::id), 1)
            where(col(Data1::id).equal(2))
        }

        // when
        val actual = queryFactory.updateQuery<Data1>(dsl)

        // then
        assertThat(actual).isEqualTo(reactiveQuery)

        verify(exactly = 1) {
            queryFactory.updateQuery(Data1::class, dsl)
        }

        confirmVerified(queryFactory)
    }

    @Test
    fun deleteQuery() {
        // given
        every { queryFactory.deleteQuery<Data1>(any(), any()) } returns reactiveQuery

        val dsl: SpringDataCriteriaDeleteQueryDsl.() -> Unit = {
            where(col(Data1::id).equal(1))
        }

        // when
        val actual = queryFactory.deleteQuery<Data1>(dsl)

        // then
        assertThat(actual).isEqualTo(reactiveQuery)

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
    fun pageQuery() = runBlocking {
        // given
        every { queryFactory.pageQuery<Data1>(any(), any(), any()) } returns CompletableFuture.completedFuture(page)

        val pageable = PageRequest.of(1, 10)

        val dsl: SpringDataPageableQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual: Page<Data1> = queryFactory.pageQuery(pageable, dsl).await()

        // then
        assertThat(actual).isEqualTo(page)

        coVerify(exactly = 1) {
            queryFactory.pageQuery(Data1::class.java, pageable, dsl)
        }

        confirmVerified(queryFactory)
    }

    @Test
    fun `pageQuery with countProjection`() = runBlocking {
        // given
        coEvery { queryFactory.pageQuery<Data1>(any(), any(), any(), any()) } returns CompletableFuture.completedFuture(
            page
        )

        val pageable = PageRequest.of(1, 10)

        val dsl: SpringDataPageableQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        val countProjection: SpringDataPageableQueryDsl<Long>.() -> SingleSelectClause<Long> = {
            select(count(column(Data1::id)))
        }

        // when
        val actual: Page<Data1> = queryFactory.pageQuery(pageable, dsl, countProjection).await()

        // then
        assertThat(actual).isEqualTo(page)

        coVerify(exactly = 1) {
            queryFactory.pageQuery(Data1::class.java, pageable, dsl, countProjection)
        }

        confirmVerified(queryFactory)
    }

    data class Data1(
        val id: Int = 100
    )
}
