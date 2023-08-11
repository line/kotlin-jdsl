package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlDerivedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
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
class JpqlDerivedEntitySerializerTest : WithAssertions {
    private val sut = JpqlDerivedEntitySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlDerivedEntity::class)
    }

    @Test
    fun `serialize - WHEN statement is select and clause is delete, THEN draw only alias`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Entities.derivedEntity(
            selectQuery = mockkClass(type = JpqlSelectQuery::class, relaxed = true),
            alias = "alias",
        )
        val context = TestRenderContext(serializer, JpqlRenderStatement.Select, JpqlRenderClause.DeleteFrom)

        // when
        sut.serialize(part as JpqlDerivedEntity, writer, context)

        // then
        verifySequence {
            writer.write("alias")
        }
    }

    @Test
    fun `serialize - WHEN statement is select and clause is from, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Entities.derivedEntity(
            selectQuery = mockkClass(type = JpqlSelectQuery::class, relaxed = true),
            alias = "alias",
        )
        val context = TestRenderContext(serializer, JpqlRenderStatement.Select, JpqlRenderClause.From)

        // when
        sut.serialize(part as JpqlDerivedEntity, writer, context)

        // then
        verifySequence {
            writer.write("(")
            serializer.serialize(part.selectQuery, writer, context)
            writer.write(")")

            writer.write(" ")
            writer.write("AS")
            writer.write(" ")

            writer.write(part.alias)
        }
    }

    @Test
    fun `serialize - WHEN statement is update and clause is update, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Entities.derivedEntity(
            selectQuery = mockkClass(type = JpqlSelectQuery::class, relaxed = true),
            alias = "alias",
        )
        val context = TestRenderContext(serializer, JpqlRenderStatement.Update, JpqlRenderClause.Update)

        // when
        sut.serialize(part as JpqlDerivedEntity, writer, context)

        // then
        verifySequence {
            writer.write("(")
            serializer.serialize(part.selectQuery, writer, context)
            writer.write(")")

            writer.write(" ")
            writer.write("AS")
            writer.write(" ")

            writer.write(part.alias)
        }
    }

    @Test
    fun `serialize - WHEN statement is delete and clause is delete, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Entities.derivedEntity(
            selectQuery = mockkClass(type = JpqlSelectQuery::class, relaxed = true),
            alias = "alias",
        )
        val context = TestRenderContext(serializer, JpqlRenderStatement.Delete, JpqlRenderClause.DeleteFrom)

        // when
        sut.serialize(part as JpqlDerivedEntity, writer, context)

        // then
        verifySequence {
            writer.write("(")
            serializer.serialize(part.selectQuery, writer, context)
            writer.write(")")

            writer.write(" ")
            writer.write("AS")
            writer.write(" ")

            writer.write(part.alias)
        }
    }
}
