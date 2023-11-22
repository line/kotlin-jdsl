package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntity
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.serializer.StatementClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.StatementClauseSource
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import kotlin.reflect.KClass

@JpqlSerializerTest
internal class JpqlEntitySerializerTest : WithAssertions {
    private val sut = JpqlEntitySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var introspector: JpqlRenderIntrospector

    private val entityDescription1 = object : JpqlEntityDescription {
        override val name = "entityName1"
    }

    private val alias1 = "alias1"

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlEntity::class)
    }

    @ParameterizedTest
    @StatementClauseSource(
        includes = [
            StatementClause(JpqlRenderStatement.Select::class, JpqlRenderClause.From::class),
            StatementClause(JpqlRenderStatement.Update::class, JpqlRenderClause.Update::class),
            StatementClause(JpqlRenderStatement.Delete::class, JpqlRenderClause.DeleteFrom::class),
        ],
    )
    fun serialize(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        every { introspector.introspect(any<KClass<*>>()) } returns entityDescription1

        val part = Entities.entity(Book::class, alias1)
        val context = TestRenderContext(introspector, statement, clause)

        // when
        sut.serialize(part as JpqlEntity<*>, writer, context)

        // then
        verifySequence {
            introspector.introspect(Book::class)
            writer.write(entityDescription1.name)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
            writer.write(alias1)
        }
    }

    @ParameterizedTest
    @StatementClauseSource(
        excludes = [
            StatementClause(JpqlRenderStatement.Select::class, JpqlRenderClause.From::class),
            StatementClause(JpqlRenderStatement.Update::class, JpqlRenderClause.Update::class),
            StatementClause(JpqlRenderStatement.Delete::class, JpqlRenderClause.DeleteFrom::class),
        ],
    )
    fun `serialize() draws only the alias, when given the statement and clause of the source`(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        val part = Entities.entity(Book::class, alias1)
        val context = TestRenderContext(introspector, statement, clause)

        // when
        sut.serialize(part as JpqlEntity<*>, writer, context)

        // then
        verifySequence {
            writer.write(alias1)
        }
    }
}
