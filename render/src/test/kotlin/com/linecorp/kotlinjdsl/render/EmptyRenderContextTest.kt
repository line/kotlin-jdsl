package com.linecorp.kotlinjdsl.render

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class EmptyRenderContextTest : WithAssertions {
    private val sut = EmptyRenderContext

    private val element1 = TestRenderContextElement1()

    private class TestRenderContextElement1 : AbstractRenderContextElement(Key) {
        companion object Key : RenderContext.Key<TestRenderContextElement1>
    }

    @Test
    fun get() {
        // when
        val actual = sut[TestRenderContextElement1]

        // then
        assertThat(actual).isNull()
    }

    @Test
    fun getValue() {
        // when
        val actual = catchException {
            sut.getValue(TestRenderContextElement1)
        }

        // then
        assertThat(actual)
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessageContaining("Key")
            .hasMessageContaining("is missing in the context.")
    }

    @Test
    fun fold() {
        // when
        val actual = sut.fold<RenderContext>(element1) { acc, element -> acc + element }

        // then
        assertThat(actual).isEqualTo(element1)
    }

    @Test
    fun plus() {
        // when
        val actual = sut + element1

        // then
        assertThat(actual).isEqualTo(element1)
    }

    @Test
    fun minusKey() {
        // when
        val actual = sut.minusKey(TestRenderContextElement1)

        // then
        assertThat(actual).isEqualTo(sut)
    }
}
