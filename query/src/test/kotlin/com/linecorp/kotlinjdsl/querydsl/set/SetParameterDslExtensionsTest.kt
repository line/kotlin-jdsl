package com.linecorp.kotlinjdsl.querydsl.set

import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class SetParameterDslExtensionsTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var dsl: SetParameterDsl

    @Test
    fun `setParams vararg`() {
        // given
        val columnSpec: ColumnSpec<String> = mockk()

        with(dsl) {
            every { setParams(columnSpec to null) } just runs
        }

        // when
        with(dsl) { setParams(columnSpec to null) }

        // then
        verify(exactly = 1) {
            with(dsl) { setParams(columnSpec to null) }
        }

        confirmVerified(dsl)
    }

    @Test
    fun setParams() {
        // given
        val columnSpec: ColumnSpec<String> = mockk()

        with(dsl) {
            every { setParams(mapOf(columnSpec to null)) } just runs
        }

        // when
        with(dsl) { setParams(mapOf(columnSpec to null)) }

        // then
        verify(exactly = 1) {
            with(dsl) { setParams(mapOf(columnSpec to null)) }
        }

        confirmVerified(dsl)
    }

    @Test
    fun set() {
        // given
        val columnSpec: ColumnSpec<String> = mockk()

        with(dsl) {
            every { set(columnSpec, null) } just runs
        }

        // when
        with(dsl) { set(columnSpec, null) }

        // then
        verify(exactly = 1) {
            with(dsl) { set(columnSpec, null) }
        }

        confirmVerified(dsl)
    }

}
