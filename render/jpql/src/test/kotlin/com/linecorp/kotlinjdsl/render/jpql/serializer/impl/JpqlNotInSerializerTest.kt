package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotIn
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlNotInSerializerTest : WithAssertions {
    private val sut = JpqlNotInSerializer()
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
        assertThat(actual).isEqualTo(JpqlNotIn::class)
    }

    @Test
    fun `serialize - WHEN compareValues is empty, THEN do not write NOT IN clause`() {
        // Given
        val part = Predicates.notIn(Expressions.expression(TestEntity::class, "test"), emptyList())
        val context = TestRenderContext(serializer)

        // When
        sut.serialize(part as JpqlNotIn<*>, writer, context)

        // Then
        verify(exactly = 0) { writer.writeParam("NOT IN") }
    }


    @Test
    fun `serialize - WHEN predicates is not empty, THEN draw all predicates with NOT IN and separated by commas`() {
        // given
        every { writer.writeEach<Expression<*>>(any(), any(), any(), any(), any()) } answers {

            val expressions: List<Expression<*>> = arg(0)
            val write: (Expression<*>) -> Unit = arg(4)

            expressions.forEach { expression -> write(expression) }
        }

        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Predicates.notIn(
            Expressions.expression(TestEntity::class, "test"),
            compareValues = listOf(
                mockkClass(Expression::class),
                mockkClass(Expression::class),
                mockkClass(Expression::class),
            ),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNotIn<*>, writer, context)

        // then
        verifySequence {
            writer.write("NOT IN")
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
