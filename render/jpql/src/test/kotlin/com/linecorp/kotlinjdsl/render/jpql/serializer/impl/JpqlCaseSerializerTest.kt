package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlCase
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlCaseSerializerTest : WithAssertions {
    private val sut = JpqlCaseSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlCase::class)
    }

    @Test
    fun `when else is null, just draw case whens and thens`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { writer.writeEach<Map.Entry<Predicate, Expression<*>>>(any(), any(), any(), any(), any()) } answers {
            val entries: Set<Map.Entry<Predicate, Expression<*>>> = arg(0)
            val write: (Map.Entry<Predicate, Expression<*>>) -> Unit = arg(4)

            entries.forEach { predicate -> write(predicate) }
        }
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.case(
            whens = mapOf(
                mockkClass(Predicate::class) to mockkClass(Expression::class),
                mockkClass(Predicate::class) to mockkClass(Expression::class),
            )
        )

        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlCase<*>, writer, context)

        // then
        verifySequence {
            writer.write("CASE")
            writer.write(" ")
            writer.writeEach(part.whens.entries, " ", "", "", any())
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(part.whens.keys.elementAt(0), writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(part.whens.values.elementAt(0), writer, context)
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(part.whens.keys.elementAt(1), writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(part.whens.values.elementAt(1), writer, context)
            writer.write(" ")
            writer.write("END")
        }
    }

    @Test
    fun `when else is null, draw case whens and thens with else`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { writer.writeEach<Map.Entry<Predicate, Expression<*>>>(any(), any(), any(), any(), any()) } answers {
            val entries: Set<Map.Entry<Predicate, Expression<*>>> = arg(0)
            val write: (Map.Entry<Predicate, Expression<*>>) -> Unit = arg(4)

            entries.forEach { predicate -> write(predicate) }
        }
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Expressions.case(
            whens = mapOf(
                mockkClass(Predicate::class) to mockkClass(Expression::class),
                mockkClass(Predicate::class) to mockkClass(Expression::class),
            ),
            `else` = mockkClass(Expression::class) {
                every { toExpression() } returns mockkClass(Expression::class)
            }
        )

        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlCase<*>, writer, context)

        // then
        verifySequence {
            writer.write("CASE")
            writer.write(" ")
            writer.writeEach(part.whens.entries, " ", "", "", any())
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(part.whens.keys.elementAt(0), writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(part.whens.values.elementAt(0), writer, context)
            writer.write("WHEN")
            writer.write(" ")
            serializer.serialize(part.whens.keys.elementAt(1), writer, context)
            writer.write(" ")
            writer.write("THEN")
            writer.write(" ")
            serializer.serialize(part.whens.values.elementAt(1), writer, context)
            writer.write(" ")
            writer.write("ELSE")
            writer.write(" ")
            serializer.serialize(part.`else`!!, writer, context)
            writer.write(" ")
            writer.write("END")
        }
    }
}
