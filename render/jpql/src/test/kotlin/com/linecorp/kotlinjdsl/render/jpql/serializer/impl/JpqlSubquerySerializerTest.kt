package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSubquery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.Selects
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
class JpqlSubquerySerializerTest : WithAssertions {
    private val sut = JpqlSubquerySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlSubquery::class)
    }

    @Test
    fun `serialize - WHEN subquery is given, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val select = Selects.select(
            returnType = TestTable1::class,
            distinct = false,
            select = emptyList(),
            from = emptyList(),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )
        val part = Expressions.subquery(select)
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSubquery<*>, writer, context)

        // then
        verifySequence {
            writer.write("(")
            serializer.serialize(part.selectQuery, writer, context)
            writer.write(")")
        }
    }

    private class TestTable1
}
