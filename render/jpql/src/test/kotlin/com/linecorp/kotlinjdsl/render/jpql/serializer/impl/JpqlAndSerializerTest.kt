package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlAnd
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlAndSerializerTest : WithAssertions {
    private val sut = JpqlAndSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlAnd::class)
    }

    @Test
    fun `serialize - WHEN predicates is empty, THEN draw 1 = 1`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Predicates.and(emptyList())
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlAnd, writer, context)

        // then
        verifySequence {
            writer.write("1 = 1")
        }
    }

    @Test
    fun `serialize - WHEN predicates is not empty, THEN draw all predicates with AND`() {
        // given
        every { writer.writeEach<Predicate>(any(), any(), any(), any(), any()) } answers {
            val predicates: List<Predicate> = arg(0)
            val write: (Predicate) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Predicates.and(
            listOf(
                mockkClass(Predicate::class),
                mockkClass(Predicate::class),
                mockkClass(Predicate::class),
            ),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlAnd, writer, context)

        // then
        verifySequence {
            writer.writeEach(part.predicates, " AND ", "", "", any())
            serializer.serialize(part.predicates.elementAt(0), writer, context)
            serializer.serialize(part.predicates.elementAt(1), writer, context)
            serializer.serialize(part.predicates.elementAt(2), writer, context)
        }
    }
}
