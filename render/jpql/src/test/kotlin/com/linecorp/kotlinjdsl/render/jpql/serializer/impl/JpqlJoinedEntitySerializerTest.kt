package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.from.impl.JpqlJoinedEntity
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlJoinedEntitySerializerTest : WithAssertions {
    private val sut = JpqlJoinedEntitySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlJoinedEntity::class)
    }

    @Test
    fun `serialize - WHEN joined entity is given, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = mockkClass(type = JpqlJoinedEntity::class, relaxed = true)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.entity, writer, context)
            writer.write(" ")
            serializer.serialize(part.join, writer, context)
        }
    }
}
