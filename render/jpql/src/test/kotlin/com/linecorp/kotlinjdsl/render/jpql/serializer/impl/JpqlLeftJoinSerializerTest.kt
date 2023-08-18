package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlLeftJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlLeftJoinSerializerTest : WithAssertions {
    private val sut = JpqlLeftJoinSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlLeftJoin::class)
    }

    @Test
    fun `serialize - WHEN left join is given, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Joins.leftJoin(
            Entities.entity(TestTable1::class),
            Predicates.isNull(Expressions.stringLiteral("value")),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlLeftJoin<*>, writer, context)

        // then
        verifySequence {
            writer.write("LEFT JOIN")
            writer.write(" ")

            serializer.serialize(part.entity, writer, context)

            writer.write(" ")
            writer.write("ON")
            writer.write(" ")

            serializer.serialize(part.on, writer, context)
        }
    }

    private class TestTable1
}
