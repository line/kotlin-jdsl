package com.linecorp.kotlinjdsl.query.spec.expression

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import jakarta.persistence.criteria.*

@ExtendWith(MockKExtension::class)
class NestedColumnSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var updateQuery: CriteriaUpdate<*>

    @MockK
    private lateinit var deleteQuery: CriteriaDelete<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun toCriteriaExpression() {
        // given
        val from = mockk<From<*, Data>>()
        val path = mockk<Path<NestedData>>()
        val nestedPath = mockk<Path<Long>>()

        every { froms[any<EntitySpec<Data>>()] } returns from
        every { from.get<NestedData>(any<String>()) } returns path
        every { path.get<Long>(any<String>()) } returns nestedPath

        // when
        val spec = NestedColumnSpec<Long>(
            ColumnSpec<NestedData>(EntitySpec(Data::class.java), "nestedData"),
            "nestedId"
        )

        val actual = spec.toCriteriaExpression(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(nestedPath)

        verify {
            froms[EntitySpec(Data::class.java)]
            from.get<NestedData>("nestedData")
            path.get<Long>("nestedId")
        }

        confirmVerified(from, froms, query, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaExpression`() {
        // given
        val from = mockk<From<*, Data>>()
        val path = mockk<Path<NestedData>>()
        val nestedPath = mockk<Path<Long>>()

        every { froms[any<EntitySpec<Data>>()] } returns from
        every { from.get<NestedData>(any<String>()) } returns path
        every { path.get<Long>(any<String>()) } returns nestedPath

        // when
        val spec = NestedColumnSpec<Long>(
            ColumnSpec<NestedData>(EntitySpec(Data::class.java), "nestedData"),
            "nestedId"
        )

        val actual = spec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(nestedPath)

        verify {
            froms[EntitySpec(Data::class.java)]
            from.get<NestedData>("nestedData")
            path.get<Long>("nestedId")
        }

        confirmVerified(from, froms, updateQuery, criteriaBuilder)
    }

    @Test
    fun `delete toCriteriaExpression`() {
        // given
        val from = mockk<From<*, Data>>()
        val path = mockk<Path<NestedData>>()
        val nestedPath = mockk<Path<Long>>()

        every { froms[any<EntitySpec<Data>>()] } returns from
        every { from.get<NestedData>(any<String>()) } returns path
        every { path.get<Long>(any<String>()) } returns nestedPath

        // when
        val spec = NestedColumnSpec<Long>(
            ColumnSpec<NestedData>(EntitySpec(Data::class.java), "nestedData"),
            "nestedId"
        )

        val actual = spec.toCriteriaExpression(froms, deleteQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(nestedPath)

        verify {
            froms[EntitySpec(Data::class.java)]
            from.get<NestedData>("nestedData")
            path.get<Long>("nestedId")
        }

        confirmVerified(from, froms, deleteQuery, criteriaBuilder)
    }

    private class Data {
        val nestedData = NestedData()
    }

    private class NestedData {
        val nestedId: Long = 0L
    }
}
