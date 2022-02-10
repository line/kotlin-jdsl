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
import javax.persistence.criteria.AbstractQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.From

@ExtendWith(MockKExtension::class)
internal class EntitySpecTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: AbstractQuery<*>

    @MockK
    private lateinit var updateQUery: CriteriaUpdate<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun toCriteriaExpression() {
        // given
        val from = mockk<From<*, Data>>()

        every { froms[any<EntitySpec<Data>>()] } returns from

        // when
        val spec = EntitySpec(Data::class.java)

        val actual = spec.toCriteriaExpression(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(from)

        verify(exactly = 1) {
            froms[spec]
        }

        confirmVerified(froms, query, criteriaBuilder)
    }

    @Test
    fun `toCriteriaExpression with alias`() {
        // given
        val from = mockk<From<*, Data>>()

        every { froms[any<EntitySpec<Data>>()] } returns from
        every { from.alias("data1") } returns mockk()

        // when
        val spec = EntitySpec(Data::class.java, "data1")

        val actual = spec.toCriteriaExpression(froms, query, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(from)

        verify(exactly = 1) {
            froms[spec]
            from.alias("data1")
        }

        confirmVerified(froms, query, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaExpression`() {
        // given
        val from = mockk<From<*, Data>>()

        every { froms[any<EntitySpec<Data>>()] } returns from

        // when
        val spec = EntitySpec(Data::class.java)

        val actual = spec.toCriteriaExpression(froms, updateQUery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(from)

        verify(exactly = 1) {
            froms[spec]
        }

        confirmVerified(froms, updateQUery, criteriaBuilder)
    }

    @Test
    fun `update toCriteriaExpression with alias`() {
        // given
        val from = mockk<From<*, Data>>()

        every { froms[any<EntitySpec<Data>>()] } returns from
        every { from.alias("data1") } returns mockk()

        // when
        val spec = EntitySpec(Data::class.java, "data1")

        val actual = spec.toCriteriaExpression(froms, updateQUery, criteriaBuilder)

        // then
        assertThat(actual).isEqualTo(from)

        verify(exactly = 1) {
            froms[spec]
            from.alias("data1")
        }

        confirmVerified(froms, updateQUery, criteriaBuilder)
    }

    private class Data
}
