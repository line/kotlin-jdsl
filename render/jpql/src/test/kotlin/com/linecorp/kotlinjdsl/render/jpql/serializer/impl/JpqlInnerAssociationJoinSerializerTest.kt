package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.JpqlInnerAssociationJoin
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlInnerAssociationJoinSerializerTest : WithAssertions {
    private val sut = JpqlInnerAssociationJoinSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlInnerAssociationJoin::class)
    }

    @Test
    fun `serialize - WHEN predicate is not given, THEN draw inner join and alias only`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Joins.innerJoin(
            Entities.entity(TestTable1::class, "test"),
            Paths.path(TestTable1::int1),
            null,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlInnerAssociationJoin<*>, writer, context)

        // then
        verifySequence {
            writer.write("INNER JOIN")
            writer.write(" ")

            serializer.serialize(part.association, writer, context)

            writer.write(" ")

            writer.write("AS")
            writer.write(" ")
            writer.write(part.entity.alias)
        }
    }

    @Test
    fun `serialize - WHEN predicate is given, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Joins.innerJoin(
            Entities.entity(TestTable1::class, "test"),
            Paths.path(TestTable1::int1),
            Predicates.equal(
                Expressions.stringLiteral("value"),
                Expressions.stringLiteral("compare"),
            ),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlInnerAssociationJoin<*>, writer, context)

        // then
        verifySequence {
            writer.write("INNER JOIN")
            writer.write(" ")

            serializer.serialize(part.association, writer, context)

            writer.write(" ")

            writer.write("AS")
            writer.write(" ")
            writer.write(part.entity.alias)

            writer.write(" ")
            writer.write("ON")
            writer.write(" ")

            serializer.serialize(part.on!!, writer, context)
        }
    }

    private class TestTable1 {
        val int1: Int = 1
    }
}
