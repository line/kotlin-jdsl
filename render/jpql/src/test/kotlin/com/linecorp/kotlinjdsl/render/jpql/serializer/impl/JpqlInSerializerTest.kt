package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIn
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlInSerializerTest : WithAssertions {
    private val sut = JpqlInSerializer()
    data class TestEntity(val id: Long, val name: String)

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlIn::class)
    }

    @Test
    fun `serialize - WHEN compareValues is empty, THEN do not write IN clause`() {
        // Given
        val part = Predicates.`in`(Expressions.expression(TestEntity::class, "test"), emptyList())
        val context = TestRenderContext(serializer)

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verify(exactly = 0) { writer.writeParam("IN") }
    }


    @Test
    fun `serialize - WHEN predicates is not empty, THEN draw all predicates with In and separated by commas`() {
        // given
        every { writer.writeEach<Expression<*>>(any(), any(), any(), any(), any()) } answers {

            val expressions: List<Expression<*>> = arg(0)
            val write: (Expression<*>) -> Unit = arg(4)

            expressions.forEach { expression -> write(expression) }
        }

        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Predicates.`in`(
            Expressions.expression(TestEntity::class, "test"),
            compareValues = listOf(
                mockkClass(Expression::class),
                mockkClass(Expression::class),
                mockkClass(Expression::class),
            ),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlIn<*>, writer, context)

        // then
        verifySequence {
            writer.write("IN")
            writer.write(" ")
            writer.write("(")
            writer.writeEach(part.compareValues, ", ", "", "", any())
            serializer.serialize(part.compareValues.elementAt(0), writer, context)
            serializer.serialize(part.compareValues.elementAt(1), writer, context)
            serializer.serialize(part.compareValues.elementAt(2), writer, context)
            writer.write(")")
        }
    }

}
