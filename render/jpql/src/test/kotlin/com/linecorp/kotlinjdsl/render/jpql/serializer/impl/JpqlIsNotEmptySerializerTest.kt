package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIsNotEmpty
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlIsNotEmptySerializerTest : WithAssertions {
    private val sut = JpqlIsNotEmptySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlIsNotEmpty::class)
    }

    @Test
    fun `serialize - WHEN is not empty is given, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = mockkClass(JpqlIsNotEmpty::class, relaxed = true)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlIsNotEmpty, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.path, writer, context)

            writer.write(" ")
            writer.write("IS NOT EMPTY")
        }
    }
}
