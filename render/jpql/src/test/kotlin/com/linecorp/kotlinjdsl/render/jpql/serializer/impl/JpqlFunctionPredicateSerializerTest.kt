package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlFunctionPredicate
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlFunctionPredicateSerializerTest : WithAssertions {
    private val sut = JpqlFunctionPredicateSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val functionName1 = "functionName1"

    private val expression1 = Expressions.value(1)
    private val expression2 = Expressions.value(2)
    private val expression3 = Expressions.value(3)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlFunctionPredicate::class)
    }

    @Test
    fun serialize() {
        // given
        val expressions = listOf(
            expression1,
            expression2,
            expression3,
        )

        val part = Predicates.function(
            functionName1,
            expressions,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlFunctionPredicate, writer, context)

        // then
        verifySequence {
            writer.write("FUNCTION")
            writer.writeParentheses(any())
            writer.write("'")
            writer.write(functionName1)
            writer.write("'")
            writer.write(",")
            writer.write(" ")
            writer.writeEach(expressions, ", ", any())
            serializer.serialize(expression1, writer, context)
            serializer.serialize(expression2, writer, context)
            serializer.serialize(expression3, writer, context)
        }
    }

    @Test
    fun `serialize() draws only the function name, when the args is empty`() {
        // given
        val part = Predicates.function(
            functionName1,
            emptyList(),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlFunctionPredicate, writer, context)

        // then
        verifySequence {
            writer.write("FUNCTION")
            writer.writeParentheses(any())
            writer.write("'")
            writer.write(functionName1)
            writer.write("'")
        }
    }
}
