package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlPathProperty
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlPathPropertySerializerTest : WithAssertions {
    private val sut = JpqlPathPropertySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlPathProperty::class)
    }

    @Test
    fun `serialize - WHEN path property is disabled, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Paths.path(
            mockkClass(Path::class) as Path<TestTable1>,
            TestTable1::int1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlPathProperty<*, *>, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.path, writer, context)
            writer.write(".")
            writer.write(part.property.name)
        }
    }

    private class TestTable1 {
        val int1: Int = 1
    }
}
