package com.linecorp.kotlinjdsl.query.clause.from

import com.linecorp.kotlinjdsl.query.spec.CrossJoinSpec
import com.linecorp.kotlinjdsl.query.spec.FetchJoinSpec
import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.query.spec.SimpleJoinSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.persistence.criteria.*

@ExtendWith(MockKExtension::class)
internal class FromClauseTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var query: AbstractQuery<*>

    private val entitySpec1 = EntitySpec(Data1::class.java)
    private val entitySpec2 = EntitySpec(Data2::class.java)
    private val entitySpec3 = EntitySpec(Data3::class.java)
    private val entitySpec4 = EntitySpec(Data4::class.java)

    @Test
    fun join() {
        // given
        val fromEntitySpec = entitySpec1
        val fromClause = FromClause(fromEntitySpec)

        val joinSpec1 = SimpleJoinSpec(entitySpec1, entitySpec2, "data2", JoinType.INNER)
        val joinSpec2 = SimpleJoinSpec(entitySpec2, entitySpec3, "data3", JoinType.LEFT)
        val joinSpec3 = FetchJoinSpec(entitySpec2, entitySpec3, "data3", JoinType.INNER)
        val joinSpec4 = FetchJoinSpec(entitySpec2, entitySpec3, "data3", JoinType.LEFT)
        val joinSpec5 = CrossJoinSpec(entitySpec4)
        val joinClause = JoinClause(listOf(joinSpec1, joinSpec2, joinSpec3, joinSpec4, joinSpec5))

        val root = mockk<Root<Data1>>()
        val join1 = mockk<Join<Any, Any>>()
        val fetch1 = mockk<MockFetch<Any, Any>>()
        val crossJoin1 = mockk<Root<Data4>>()

        every { query.from(Data1::class.java) } returns root
        every { root.join<Any, Any>(any<String>(), any()) } returns join1
        every { join1.fetch<Any, Any>(any(), any()) } returns fetch1
        every { query.from(Data4::class.java) } returns crossJoin1

        // when
        val actual = fromClause.join(joinClause, query)

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(
            Froms(
                root = root,
                map = mapOf(
                    entitySpec1 to root,
                    entitySpec2 to join1,
                    entitySpec3 to fetch1,
                    entitySpec4 to crossJoin1,
                )
            )
        )

        verify(exactly = 1) {
            query.from(Data1::class.java)
            root.join<Any, Any>("data2", JoinType.INNER)
            join1.fetch<Any, Any>("data3", JoinType.LEFT)
            query.from(Data4::class.java)
        }

        verify {
            root.hashCode()
            join1.hashCode()
            fetch1.hashCode()
            crossJoin1.hashCode()
        }

        confirmVerified(root, join1, fetch1, crossJoin1, query)
    }

    @Test
    fun `join - if join is incomplete then throw exception`() {
        // given
        val fromEntitySpec = entitySpec1
        val fromClause = FromClause(fromEntitySpec)

        val joinSpec = SimpleJoinSpec(entitySpec2, entitySpec3, "data3", JoinType.LEFT)
        val joinClause = JoinClause(listOf(joinSpec))

        val root = mockk<Root<Data1>>()

        every { query.from(Data1::class.java) } returns root

        // when
        val exception = catchThrowable(IllegalStateException::class) {
            fromClause.join(joinClause, query)
        }

        // then
        assertThat(exception)
            .hasMessageContaining("Join clause is incomplete. Please check if the following Entities are joined")

        verify(exactly = 1) {
            query.from(Data1::class.java)
        }

        confirmVerified(root, query)
    }

    private class Data1
    private class Data2
    private class Data3
    private class Data4

    private interface MockFetch<T, R> : Join<T, R>, Fetch<T, R>
}