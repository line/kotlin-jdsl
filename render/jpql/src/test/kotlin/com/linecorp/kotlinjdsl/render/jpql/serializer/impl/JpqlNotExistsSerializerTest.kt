package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotExists
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlNotExistsSerializerTest : WithAssertions {
    private val sut = JpqlNotExistsSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlNotExists::class)
    }

    @Test
    fun `serialize - WHEN not exists is given, THEN draw not exists with subquery`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = mockkClass(type = JpqlNotExists::class, relaxed = true)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part, writer, context)

        // then
        verifySequence {
            writer.write("NOT EXISTS")
            writer.write(" ")
            writer.write("(")
            serializer.serialize(part.subquery, writer, context)
            writer.write(")")
        }
    }
}
