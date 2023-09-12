package com.linecorp.kotlinjdsl.render

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CombinedRenderContextTest : WithAssertions {
    private lateinit var sut: CombinedRenderContext

    private val element1 = TestRenderContextElement1()
    private val element2 = TestRenderContextElement2()
    private val element3 = TestRenderContextElement3()
    private val element4 = TestRenderContextElement4()

    private class TestRenderContextElement1 : AbstractRenderContextElement(Key) {
        companion object Key : RenderContext.Key<TestRenderContextElement1>
    }

    private class TestRenderContextElement2 : AbstractRenderContextElement(Key) {
        companion object Key : RenderContext.Key<TestRenderContextElement2>
    }

    private class TestRenderContextElement3 : AbstractRenderContextElement(Key) {
        companion object Key : RenderContext.Key<TestRenderContextElement3>
    }

    private class TestRenderContextElement4 : AbstractRenderContextElement(Key) {
        companion object Key : RenderContext.Key<TestRenderContextElement4>
    }

    private class TestRenderContextElement5 : AbstractRenderContextElement(Key) {
        companion object Key : RenderContext.Key<TestRenderContextElement5>
    }

    @BeforeEach
    fun setUp() {
        sut = CombinedRenderContext(
            CombinedRenderContext(
                element1,
                element2,
            ),
            element3,
        )
    }

    @Test
    fun get() {
        assertThat(sut[TestRenderContextElement1]).isEqualTo(element1)
        assertThat(sut[TestRenderContextElement2]).isEqualTo(element2)
        assertThat(sut[TestRenderContextElement3]).isEqualTo(element3)
        assertThat(sut[TestRenderContextElement4]).isNull()
        assertThat(sut[TestRenderContextElement5]).isNull()
    }

    @Test
    fun getValue() {
        assertThat(sut.getValue(TestRenderContextElement1)).isEqualTo(element1)
        assertThat(sut.getValue(TestRenderContextElement2)).isEqualTo(element2)
        assertThat(sut.getValue(TestRenderContextElement3)).isEqualTo(element3)

        assertThatThrownBy { sut.getValue(TestRenderContextElement4) }
            .hasMessageContaining("Key")
            .hasMessageContaining("is missing in the context.")

        assertThatThrownBy { sut.getValue(TestRenderContextElement5) }
            .hasMessageContaining("Key")
            .hasMessageContaining("is missing in the context.")
    }

    @Test
    fun fold() {
        // when
        val actual = sut.fold(mutableListOf<RenderContext.Element>()) { acc, element -> acc.also { it.add(element) } }

        // then
        assertThat(actual).containsOnly(
            element1,
            element2,
            element3,
        )
    }

    @Test
    fun plus() {
        // when
        val actual = sut + element4

        // then
        assertThat(actual[TestRenderContextElement1]).isEqualTo(element1)
        assertThat(actual[TestRenderContextElement2]).isEqualTo(element2)
        assertThat(actual[TestRenderContextElement3]).isEqualTo(element3)
        assertThat(actual[TestRenderContextElement4]).isEqualTo(element4)
        assertThat(actual[TestRenderContextElement5]).isNull()
    }

    @Test
    fun minusKey() {
        var actual: RenderContext = sut

        assertThat(actual[TestRenderContextElement1]).isEqualTo(element1)
        assertThat(actual[TestRenderContextElement2]).isEqualTo(element2)
        assertThat(actual[TestRenderContextElement3]).isEqualTo(element3)
        assertThat(actual[TestRenderContextElement4]).isNull()
        assertThat(actual[TestRenderContextElement5]).isNull()

        actual = sut.minusKey(TestRenderContextElement1)

        assertThat(actual[TestRenderContextElement1]).isNull()
        assertThat(actual[TestRenderContextElement2]).isEqualTo(element2)
        assertThat(actual[TestRenderContextElement3]).isEqualTo(element3)
        assertThat(actual[TestRenderContextElement4]).isNull()
        assertThat(actual[TestRenderContextElement5]).isNull()

        actual = sut.minusKey(TestRenderContextElement2)

        assertThat(actual[TestRenderContextElement1]).isEqualTo(element1)
        assertThat(actual[TestRenderContextElement2]).isNull()
        assertThat(actual[TestRenderContextElement3]).isEqualTo(element3)
        assertThat(actual[TestRenderContextElement4]).isNull()
        assertThat(actual[TestRenderContextElement5]).isNull()

        actual = sut.minusKey(TestRenderContextElement3)

        assertThat(actual[TestRenderContextElement1]).isEqualTo(element1)
        assertThat(actual[TestRenderContextElement2]).isEqualTo(element2)
        assertThat(actual[TestRenderContextElement3]).isNull()
        assertThat(actual[TestRenderContextElement4]).isNull()
        assertThat(actual[TestRenderContextElement5]).isNull()

        actual = sut.minusKey(TestRenderContextElement4)

        assertThat(actual[TestRenderContextElement1]).isEqualTo(element1)
        assertThat(actual[TestRenderContextElement2]).isEqualTo(element2)
        assertThat(actual[TestRenderContextElement3]).isEqualTo(element3)
        assertThat(actual[TestRenderContextElement4]).isNull()
        assertThat(actual[TestRenderContextElement5]).isNull()

        actual = sut.minusKey(TestRenderContextElement5)

        assertThat(actual[TestRenderContextElement1]).isEqualTo(element1)
        assertThat(actual[TestRenderContextElement2]).isEqualTo(element2)
        assertThat(actual[TestRenderContextElement3]).isEqualTo(element3)
        assertThat(actual[TestRenderContextElement4]).isNull()
        assertThat(actual[TestRenderContextElement5]).isNull()

        actual = sut
            .minusKey(TestRenderContextElement1)
            .minusKey(TestRenderContextElement2)
            .minusKey(TestRenderContextElement3)
            .minusKey(TestRenderContextElement4)
            .minusKey(TestRenderContextElement5)

        assertThat(actual[TestRenderContextElement1]).isNull()
        assertThat(actual[TestRenderContextElement2]).isNull()
        assertThat(actual[TestRenderContextElement3]).isNull()
        assertThat(actual[TestRenderContextElement4]).isNull()
        assertThat(actual[TestRenderContextElement5]).isNull()
    }

    @Test
    fun equals() {
        // given
        val actual = element1 + element2 + element3

        // when
        assertThat(actual).isEqualTo(sut)
    }
}
