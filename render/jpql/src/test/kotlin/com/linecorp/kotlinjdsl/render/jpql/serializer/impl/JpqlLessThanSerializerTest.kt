package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlLessThan
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlLessThanSerializerTest : WithAssertions {
    private val sut = JpqlLessThanSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Expressions.value(1)
    private val expression2 = Expressions.value(2)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlLessThan::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Predicates.lessThan(
            expression1,
            expression2,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLessThan<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("<")
            writer.write(" ")
            serializer.serialize(expression2, writer, context)
        }
    }
}
