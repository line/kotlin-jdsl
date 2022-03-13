package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.CriteriaDeleteQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ReactiveQueryFactoryExtensionsTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var queryFactory: ReactiveQueryFactory

    @MockK
    private lateinit var query: ReactiveQuery<Data1>

    @MockK
    private lateinit var subqueryExpressionSpec: SubqueryExpressionSpec<Data1>

    @Test
    fun singleQuery() = runBlocking {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns query
        coEvery { query.singleResult() } returns Data1()

        val dsl: CriteriaQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual: Data1 = queryFactory.singleQuery(dsl)

        // then
        assertThat(actual).isEqualTo(Data1())

        verify(exactly = 1) {
            queryFactory.selectQuery(Data1::class.java, dsl)
        }

        coVerify(exactly = 1) {
            query.singleResult()
        }

        confirmVerified(queryFactory, query)
    }

    @Test
    fun listQuery() = runBlocking {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns query
        coEvery { query.resultList() } returns listOf(Data1())

        val dsl: CriteriaQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual: List<Data1> = queryFactory.listQuery(dsl)

        // then
        assertThat(actual).isEqualTo(listOf(Data1()))

        verify(exactly = 1) {
            queryFactory.selectQuery(Data1::class.java, dsl)
        }

        coVerify(exactly = 1) {
            query.resultList()
        }

        confirmVerified(queryFactory, query)
    }

    @Test
    fun selectQuery() {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns query

        val dsl: CriteriaQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual: ReactiveQuery<Data1> = queryFactory.selectQuery(dsl)

        // then
        assertThat(actual).isEqualTo(query)

        verify {
            queryFactory.selectQuery(Data1::class.java, dsl)
        }

        confirmVerified(queryFactory)
    }

    @Test
    fun updateQuery() {
        // given
        every { queryFactory.updateQuery<Data1>(any(), any()) } returns query

        val dsl: CriteriaUpdateQueryDsl.() -> Unit = {
            set(col(Data1::id), 1)
            where(col(Data1::id).equal(2))
        }

        // when
        val actual: ReactiveQuery<Data1> = queryFactory.updateQuery(dsl)

        // then
        assertThat(actual).isEqualTo(query)

        verify(exactly = 1) {
            queryFactory.updateQuery(Data1::class, dsl)
        }

        confirmVerified(queryFactory)
    }

    @Test
    fun deleteQuery() {
        // given
        every { queryFactory.deleteQuery<Data1>(any(), any()) } returns query

        val dsl: CriteriaDeleteQueryDsl.() -> Unit = {
            where(col(Data1::id).equal(1))
        }

        // when
        val actual: ReactiveQuery<Data1> = queryFactory.deleteQuery(dsl)

        // then
        assertThat(actual).isEqualTo(query)

        verify(exactly = 1) {
            queryFactory.deleteQuery(Data1::class, dsl)
        }

        confirmVerified(queryFactory)
    }

    @Test
    fun subquery() {
        // given
        every { queryFactory.subquery<Data1>(any(), any()) } returns subqueryExpressionSpec

        val dsl: SubqueryDsl<Data1>.() -> Unit = {
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

    data class Data1(
        val id: Int = 100
    )
}
