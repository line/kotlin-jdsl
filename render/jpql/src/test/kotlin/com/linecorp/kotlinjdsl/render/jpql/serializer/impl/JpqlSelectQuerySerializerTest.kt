package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.from.From
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.Selects
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
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
class JpqlSelectQuerySerializerTest : WithAssertions {
    private val sut = JpqlSelectQuerySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlSelectQuery::class)
    }

    @Test
    fun `serialize - WHEN all optional properties is empty, THEN draw only select and from`() {
        // given
        every { writer.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
            val predicates: List<Any> = arg(0)
            val write: (Any) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Selects.select(
            returnType = TestTable1::class,
            distinct = false,
            select = emptyList(),
            from = emptyList(),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")

            writer.writeEach(part.select, ", ", "", "", any())

            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")

            writer.writeEach(part.from, separator = ", ", "", "", any())
        }
    }

    @Test
    fun `serialize - WHEN distinct is enabled, THEN draw DISTINCT`() {
        // given
        every { writer.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
            val predicates: List<Any> = arg(0)
            val write: (Any) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Selects.select(
            returnType = TestTable1::class,
            distinct = true,
            select = emptyList(),
            from = emptyList(),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")

            writer.write("DISTINCT")
            writer.write(" ")

            writer.writeEach(part.select, ", ", "", "", any())

            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")

            writer.writeEach(part.from, separator = ", ", "", "", any())
        }
    }

    @Test
    fun `serialize - WHEN select is not empty, THEN draw select elements`() {
        // given
        every { writer.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
            val predicates: List<Any> = arg(0)
            val write: (Any) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Selects.select(
            returnType = TestTable1::class,
            distinct = false,
            select = listOf(
                Expressions.stringLiteral("field1"),
                Expressions.stringLiteral("field2"),
            ),
            from = emptyList(),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")

            writer.writeEach(part.select, ", ", "", "", any())
            serializer.serialize(
                part.select.elementAt(0),
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            serializer.serialize(
                part.select.elementAt(1),
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )

            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")

            writer.writeEach(part.from, separator = ", ", "", "", any())
        }
    }

    @Test
    fun `serialize - WHEN from is not empty, THEN draw from elements`() {
        // given
        every { writer.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
            val predicates: List<Any> = arg(0)
            val write: (Any) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Selects.select(
            returnType = TestTable1::class,
            distinct = false,
            select = emptyList(),
            from = listOf(
                mockkClass(From::class),
                mockkClass(From::class),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")

            writer.writeEach(part.select, ", ", "", "", any())

            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")

            writer.writeEach(part.from, separator = ", ", "", "", any())
            serializer.serialize(
                part.from.elementAt(0),
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
            serializer.serialize(
                part.from.elementAt(1),
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
        }
    }

    @Test
    fun `serialize - WHEN where is not null, THEN draw where clause`() {
        // given
        every { writer.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
            val predicates: List<Any> = arg(0)
            val write: (Any) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Selects.select(
            returnType = TestTable1::class,
            distinct = false,
            select = emptyList(),
            from = emptyList(),
            where = Predicates.isNull(Expressions.stringLiteral("field1")),
            groupBy = null,
            having = null,
            orderBy = null,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")

            writer.writeEach(part.select, ", ", "", "", any())

            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")

            writer.writeEach(part.from, separator = ", ", "", "", any())

            writer.write(" ")
            writer.write("WHERE")
            writer.write(" ")

            serializer.serialize(part.where!!, writer, context + JpqlRenderStatement.Select + JpqlRenderClause.Where)
        }
    }

    @Test
    fun `serialize - WHEN group by is not null, THEN draw group by clause`() {
        // given
        every { writer.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
            val predicates: List<Any> = arg(0)
            val write: (Any) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Selects.select(
            returnType = TestTable1::class,
            distinct = false,
            select = emptyList(),
            from = emptyList(),
            where = null,
            groupBy = listOf(
                Expressions.stringLiteral("field1"),
                Expressions.stringLiteral("field2"),
            ),
            having = null,
            orderBy = null,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")

            writer.writeEach(part.select, ", ", "", "", any())

            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")

            writer.writeEach(part.from, separator = ", ", "", "", any())

            writer.write(" ")
            writer.write("GROUP BY")
            writer.write(" ")

            writer.writeEach(part.groupBy!!, separator = ", ", "", "", any())
            serializer.serialize(
                part.groupBy!!.elementAt(0),
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.GroupBy,
            )
            serializer.serialize(
                part.groupBy!!.elementAt(1),
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.GroupBy,
            )
        }
    }

    @Test
    fun `serialize - WHEN having is not null, THEN draw having clause`() {
        // given
        every { writer.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
            val predicates: List<Any> = arg(0)
            val write: (Any) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Selects.select(
            returnType = TestTable1::class,
            distinct = false,
            select = emptyList(),
            from = emptyList(),
            where = null,
            groupBy = emptyList(),
            having = Predicates.isNull(Expressions.stringLiteral("field1")),
            orderBy = null,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")

            writer.writeEach(part.select, ", ", "", "", any())

            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")

            writer.writeEach(part.from, separator = ", ", "", "", any())

            writer.write(" ")
            writer.write("HAVING")
            writer.write(" ")

            serializer.serialize(part.having!!, writer, context + JpqlRenderStatement.Select + JpqlRenderClause.Having)
        }
    }

    @Test
    fun `serialize - WHEN order by is not null, THEN draw order by clause`() {
        // given
        every { writer.writeEach<Any>(any(), any(), any(), any(), any()) } answers {
            val predicates: List<Any> = arg(0)
            val write: (Any) -> Unit = arg(4)

            predicates.forEach { predicate -> write(predicate) }
        }
        every { writer.write(any<String>()) } just runs
        every { serializer.serialize(any(), any(), any()) } just runs

        val part = Selects.select(
            returnType = TestTable1::class,
            distinct = false,
            select = emptyList(),
            from = emptyList(),
            where = null,
            groupBy = emptyList(),
            having = null,
            orderBy = listOf(
                Sorts.asc(Expressions.stringLiteral("field1"), null),
                Sorts.desc(Expressions.stringLiteral("field2"), null),
            ),
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")

            writer.writeEach(part.select, ", ", "", "", any())

            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")

            writer.writeEach(part.from, separator = ", ", "", "", any())

            writer.write(" ")
            writer.write("ORDER BY")
            writer.write(" ")

            writer.writeEach(part.orderBy!!, separator = ", ", "", "", any())
            serializer.serialize(
                part.orderBy!!.elementAt(0),
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.OrderBy,
            )
            serializer.serialize(
                part.orderBy!!.elementAt(1),
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.OrderBy,
            )
        }
    }

    private class TestTable1
}
