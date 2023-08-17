package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlNotInSubquery
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
class JpqlNotInSubquerySerializerTest : WithAssertions {
    private val sut = JpqlNotInSubquerySerializer()
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
        assertThat(actual).isEqualTo(JpqlNotInSubquery::class)
    }

    @Test
    fun `serialize - WHEN not in subquery is given, THEN draw full syntax`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val select = Selects.select(
            returnType = TestEntity::class,
            distinct = false,
            select = emptyList(),
            from = emptyList(),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )
        val part = Predicates.notIn(Expressions.expression(TestEntity::class, "test"), Expressions.subquery(select))
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlNotInSubquery<*>, writer, context)

        // then
        verifySequence {
            serializer.serialize(part.value, writer, context)

            writer.write(" ")
            writer.write("NOT IN")
            writer.write(" ")

            serializer.serialize(part.subquery, writer, context)
        }
    }
}
