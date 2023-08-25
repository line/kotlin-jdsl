package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.update.Updates
import com.linecorp.kotlinjdsl.querymodel.jpql.update.impl.JpqlUpdateQuery
import com.linecorp.kotlinjdsl.render.TestRenderContext
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
import java.math.BigDecimal

@JpqlSerializerTest
class JpqlUpdateQuerySerializerTest : WithAssertions {
    private val sut = JpqlUpdateQuerySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val entity1 = Entities.entity(Book::class, "book01")

    private val path1 = Paths.path(Book::price)
    private val path2 = Paths.path(Book::salePrice)

    private val expression1 = Expressions.value(BigDecimal.valueOf(100))
    private val expression2 = Expressions.value(BigDecimal.valueOf(50))

    private val predicate1 = Predicates.equal(Paths.path(Book::title), Expressions.value("Book01"))

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlUpdateQuery::class)
    }

    @Test
    fun serialize() {
        // given
        val pathAndExpressions: Map<Path<*>, Expression<*>> = mapOf(
            path1 to expression1,
            path2 to expression2,
        )

        val part = Updates.update(
            entity = entity1,
            set = pathAndExpressions,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlUpdateQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("UPDATE")
            writer.write(" ")
            serializer.serialize(entity1, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Update)
            writer.write(" ")
            writer.write("SET")
            writer.write(" ")
            writer.writeEach(pathAndExpressions.entries, ", ", "", "", any())
            serializer.serialize(path1, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Set)
            writer.write(" ")
            writer.write("=")
            writer.write(" ")
            serializer.serialize(expression1, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Set)
            serializer.serialize(path2, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Set)
            writer.write(" ")
            writer.write("=")
            writer.write(" ")
            serializer.serialize(expression2, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Set)
        }
    }

    @Test
    fun `serialize() draws the WHERE, when the where is not null`() {
        // given
        val pathAndExpressions: Map<Path<*>, Expression<*>> = mapOf(
            path1 to expression1,
            path2 to expression2,
        )

        val part = Updates.update(
            entity = entity1,
            set = pathAndExpressions,
            where = predicate1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlUpdateQuery<*>, writer, context)

        // then
        verifySequence {
            writer.write("UPDATE")
            writer.write(" ")
            serializer.serialize(entity1, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Update)
            writer.write(" ")
            writer.write("SET")
            writer.write(" ")
            writer.writeEach(pathAndExpressions.entries, ", ", "", "", any())
            serializer.serialize(path1, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Set)
            writer.write(" ")
            writer.write("=")
            writer.write(" ")
            serializer.serialize(expression1, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Set)
            serializer.serialize(path2, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Set)
            writer.write(" ")
            writer.write("=")
            writer.write(" ")
            serializer.serialize(expression2, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Set)
            writer.write(" ")
            writer.write("WHERE")
            writer.write(" ")
            serializer.serialize(predicate1, writer, context + JpqlRenderStatement.Update + JpqlRenderClause.Where)
        }
    }
}
