package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.Selects
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlSelectQuerySerializerTest : WithAssertions {
    private val sut = JpqlSelectQuerySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Paths.path(Book::price)
    private val expression2 = Paths.path(Book::salePrice)
    private val expression3 = Paths.path(Book::isbn)
    private val expression4 = Paths.path(Book::title)

    private val entity1 = Entities.entity(Book::class)
    private val entity2 = Entities.entity(Author::class)

    private val predicate1 = Predicates.equal(Paths.path(Book::title), Expressions.value("Book01"))

    private val sort1 = Sorts.asc(Paths.path(Book::isbn))
    private val sort2 = Sorts.asc(Paths.path(Book::title))

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlSelectQuery::class)
    }

    @Test
    fun serialize() {
        // given
        val expressions = listOf(
            expression1,
            expression2,
        )

        val entities = listOf(
            entity1,
            entity2,
        )

        val part = Selects.select(
            returnType = String::class,
            distinct = false,
            select = expressions,
            from = entities,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")
            writer.writeEach(expressions, ", ", "", "", any())
            serializer.serialize(
                expression1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            serializer.serialize(
                expression2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")
            writer.writeEach(entities, separator = ", ", "", "", any())
            serializer.serialize(
                entity1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
            serializer.serialize(
                entity2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
        }
    }

    @Test
    fun `serialize() draws the DISTINCT, when the distinct is enabled`() {
        // given
        val expressions = listOf(
            expression1,
            expression2,
        )

        val entities = listOf(
            entity1,
            entity2,
        )

        val part = Selects.select(
            returnType = String::class,
            distinct = true,
            select = expressions,
            from = entities,
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
            writer.writeEach(expressions, ", ", "", "", any())
            serializer.serialize(
                expression1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            serializer.serialize(
                expression2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")
            writer.writeEach(entities, separator = ", ", "", "", any())
            serializer.serialize(
                entity1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
            serializer.serialize(
                entity2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
        }
    }

    @Test
    fun `serialize() draws the WHERE, when the where is not null`() {
        // given
        val expressions = listOf(
            expression1,
            expression2,
        )

        val entities = listOf(
            entity1,
            entity2,
        )

        val part = Selects.select(
            returnType = String::class,
            distinct = false,
            select = expressions,
            from = entities,
            where = predicate1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")
            writer.writeEach(expressions, ", ", "", "", any())
            serializer.serialize(
                expression1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            serializer.serialize(
                expression2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")
            writer.writeEach(entities, separator = ", ", "", "", any())
            serializer.serialize(
                entity1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
            serializer.serialize(
                entity2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
            writer.write(" ")
            writer.write("WHERE")
            writer.write(" ")
            serializer.serialize(
                predicate1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Where,
            )
        }
    }

    @Test
    fun `serialize() draws the GROUP BY, when the groupBy is not null`() {
        // given
        val expressions1 = listOf(
            expression1,
            expression2,
        )

        val expressions2 = listOf(
            expression3,
            expression4,
        )

        val entities = listOf(
            entity1,
            entity2,
        )

        val part = Selects.select(
            returnType = String::class,
            distinct = false,
            select = expressions1,
            from = entities,
            groupBy = expressions2,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")
            writer.writeEach(expressions1, ", ", "", "", any())
            serializer.serialize(
                expression1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            serializer.serialize(
                expression2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")
            writer.writeEach(entities, separator = ", ", "", "", any())
            serializer.serialize(
                entity1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
            serializer.serialize(
                entity2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
            writer.write(" ")
            writer.write("GROUP BY")
            writer.write(" ")
            writer.writeEach(expressions2, ", ", "", "", any())
            serializer.serialize(
                expression3,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.GroupBy,
            )
            serializer.serialize(
                expression4,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.GroupBy,
            )
        }
    }

    @Test
    fun `serialize() draws the HAVING, when the having is not null`() {
        // given
        val expressions = listOf(
            expression1,
            expression2,
        )

        val entities = listOf(
            entity1,
            entity2,
        )

        val part = Selects.select(
            returnType = String::class,
            distinct = false,
            select = expressions,
            from = entities,
            having = predicate1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")
            writer.writeEach(expressions, ", ", "", "", any())
            serializer.serialize(
                expression1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            serializer.serialize(
                expression2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")
            writer.writeEach(entities, separator = ", ", "", "", any())
            serializer.serialize(
                entity1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
            serializer.serialize(
                entity2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
            writer.write(" ")
            writer.write("HAVING")
            writer.write(" ")
            serializer.serialize(
                predicate1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Having,
            )
        }
    }

    @Test
    fun `serialize() draws the ORDER BY, when the orderBy is not null`() {
        // given
        val expressions = listOf(
            expression1,
            expression2,
        )

        val sorts = listOf(
            sort1,
            sort2,
        )

        val entities = listOf(
            entity1,
            entity2,
        )

        val part = Selects.select(
            returnType = String::class,
            distinct = false,
            select = expressions,
            from = entities,
            orderBy = sorts,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlSelectQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("SELECT")
            writer.write(" ")
            writer.writeEach(expressions, ", ", "", "", any())
            serializer.serialize(
                expression1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            serializer.serialize(
                expression2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.Select,
            )
            writer.write(" ")
            writer.write("FROM")
            writer.write(" ")
            writer.writeEach(entities, separator = ", ", "", "", any())
            serializer.serialize(
                entity1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
            serializer.serialize(
                entity2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.From,
            )
            writer.write(" ")
            writer.write("ORDER BY")
            writer.write(" ")
            writer.writeEach(sorts, ", ", "", "", any())
            serializer.serialize(
                sort1,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.OrderBy,
            )
            serializer.serialize(
                sort2,
                writer,
                context + JpqlRenderStatement.Select + JpqlRenderClause.OrderBy,
            )
        }
    }
}
