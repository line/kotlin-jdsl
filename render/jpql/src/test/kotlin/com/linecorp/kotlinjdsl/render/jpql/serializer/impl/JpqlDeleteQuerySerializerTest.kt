package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.Deletes
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.impl.JpqlDeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
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
class JpqlDeleteQuerySerializerTest : WithAssertions {
    private val sut = JpqlDeleteQuerySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlDeleteQuery::class)
    }

    @Test
    fun `serialize - WHEN where is null, THEN does not draw where clause`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Deletes.delete(
            entity = mockkClass(Entity::class),
            where = null,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlDeleteQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("DELETE")
            writer.write(" ")
            serializer.serialize(
                part.entity,
                writer,
                context + JpqlRenderStatement.Delete + JpqlRenderClause.DeleteFrom
            )
        }
    }

    @Test
    fun `serialize - WHEN where is not null, THEN draw where clause`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Deletes.delete(
            entity = mockkClass(Entity::class),
            where = mockkClass(Predicate::class),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlDeleteQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("DELETE")
            writer.write(" ")
            serializer.serialize(
                part.entity,
                writer,
                context + JpqlRenderStatement.Delete + JpqlRenderClause.DeleteFrom
            )
            writer.write(" ")
            writer.write("WHERE")
            writer.write(" ")
            serializer.serialize(
                part.where!!,
                writer,
                context + JpqlRenderStatement.Delete + JpqlRenderClause.Where
            )
        }
    }
}
