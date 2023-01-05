package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.querydsl.CriteriaDeleteQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaQueryDsl
import com.linecorp.kotlinjdsl.querydsl.CriteriaUpdateQueryDsl
import com.linecorp.kotlinjdsl.querydsl.SubqueryDsl
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.stream.Stream
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import kotlin.streams.toList

@ExtendWith(MockKExtension::class)
internal class QueryFactoryExtensionsTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var queryFactory: QueryFactory

    @MockK
    private lateinit var typedQuery: TypedQuery<Data1>

    @MockK
    private lateinit var subqueryExpressionSpec: SubqueryExpressionSpec<Data1>

    @Test
    fun singleQuery() {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns typedQuery
        every { typedQuery.singleResult } returns Data1()

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
            typedQuery.singleResult
        }

        confirmVerified(queryFactory, typedQuery)
    }

    @Test
    fun listQuery() {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns typedQuery
        every { typedQuery.resultList } returns listOf(Data1())

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
            typedQuery.resultList
        }

        confirmVerified(queryFactory, typedQuery)
    }

    @Test
    fun streamQuery() {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns typedQuery
        every { typedQuery.resultStream } returns Stream.of(Data1())

        val dsl: CriteriaQueryDsl<Data1>.() -> Unit = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // when
        val actual = queryFactory.streamQuery(dsl).toList()

        // then
        assertThat(actual).contains(Data1())

        verify(exactly = 1) {
            queryFactory.selectQuery(Data1::class.java, dsl)
            typedQuery.resultStream
        }

        confirmVerified(queryFactory, typedQuery)
    }

    @Test
    fun selectQuery() {
        // given
        every { queryFactory.selectQuery<Data1>(any(), any()) } returns typedQuery

        val dsl: CriteriaQueryDsl<Data1>.() -> Unit = {
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

        val dsl: CriteriaUpdateQueryDsl.() -> Unit = {
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

        val dsl: CriteriaDeleteQueryDsl.() -> Unit = {
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
