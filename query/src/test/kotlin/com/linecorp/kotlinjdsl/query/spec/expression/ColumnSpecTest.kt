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
import javax.persistence.criteria.*

@ExtendWith(MockKExtension::class)
internal class ColumnSpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var updateQuery: CriteriaUpdate<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun toCriteriaExpression() {
        // given
        val from = mockk<From<*, Data>>()
        val path = mockk<Path<String>>()

        every { froms[any<EntitySpec<Data>>()] } returns from
        every { from.get<String>(any<String>()) } returns path

        // when
        val spec = ColumnSpec<String>(EntitySpec(Data::class.java), "name")

        val actual = spec.toCriteriaExpression(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(path)

        verify {
            froms[EntitySpec(Data::class.java)]
            from.get<String>("name")
        }

        confirmVerified(from, froms, query, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaExpression`() {
        // given
        val from = mockk<From<*, Data>>()
        val path = mockk<Path<String>>()

        every { froms[any<EntitySpec<Data>>()] } returns from
        every { from.get<String>(any<String>()) } returns path

        // when
        val spec = ColumnSpec<String>(EntitySpec(Data::class.java), "name")

        val actual = spec.toCriteriaExpression(froms, updateQuery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(path)

        verify {
            froms[EntitySpec(Data::class.java)]
            from.get<String>("name")
        }

        confirmVerified(from, froms, updateQuery, criteriaBuilder)
    }


    private class Data {
        val name = "name"
    }
}
