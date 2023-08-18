package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.update.Updates
import com.linecorp.kotlinjdsl.querymodel.jpql.update.impl.JpqlUpdateQuery
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlUpdateQuerySerializerTest : WithAssertions {
    private val sut = JpqlUpdateQuerySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlUpdateQuery::class)
    }

    @Test
    fun `serialize - WHEN where is null, THEN draw only update and set`() {
        // given
        every { writer.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
            val predicates: Iterable<Any> = arg(0)
            val write: (Any) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Updates.update(
            entity = mockkClass(Entity::class),
            set = emptyMap(),
            where = null,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlUpdateQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("UPDATE")
            writer.write(" ")

            serializer.serialize(part.entity, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Update)

            writer.write(" ")
            writer.write("SET")
            writer.write(" ")

            writer.writeEach(part.set.entries, ", ", "", "", any())
        }
    }

    @Test
    fun `serialize - WHEN set is not empty, THEN draw set elements`() {
        // given
        every { writer.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
            val predicates: Iterable<Any> = arg(0)
            val write: (Any) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Updates.update(
            entity = mockkClass(Entity::class),
            set = mapOf(
                Paths.path(TestTable1::int1) to Expressions.value("value1"),
                Paths.path(TestTable1::int1) to Expressions.value("value2"),
            ),
            where = null,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlUpdateQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("UPDATE")
            writer.write(" ")

            serializer.serialize(part.entity, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Update)

            writer.write(" ")
            writer.write("SET")
            writer.write(" ")

            writer.writeEach(part.set.entries, ", ", "", "", any())

            val fromContext = context + JpqlRenderStatement.Update + JpqlRenderClause.Set
            part.set.entries.forEach { (path, expr) ->
                serializer.serialize(path, writer, fromContext)

                writer.write(" ")
                writer.write("=")
                writer.write(" ")

                serializer.serialize(expr, writer, fromContext)
            }
        }
    }

    @Test
    fun `serialize - WHEN where is not null, THEN draw where clause`() {
        // given
        every { writer.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
            val predicates: Iterable<Any> = arg(0)
            val write: (Any) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Updates.update(
            entity = mockkClass(Entity::class),
            set = emptyMap(),
            where = Predicates.isNull(Expressions.stringLiteral("field1")),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlUpdateQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("UPDATE")
            writer.write(" ")

            serializer.serialize(part.entity, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Update)

            writer.write(" ")
            writer.write("SET")
            writer.write(" ")

            writer.writeEach(part.set.entries, ", ", "", "", any())

            writer.write(" ")
            writer.write("WHERE")
            writer.write(" ")

            serializer.serialize(part.where!!, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Where)
        }
    }

    private class TestTable1 {
        val int1 = 1
    }
}
