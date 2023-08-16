package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntity
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.*
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest

@JpqlSerializerTest
internal class JpqlEntitySerializerTest : WithAssertions {
    private val sut = JpqlEntitySerializer()
    private val entityDescription = object : JpqlEntityDescription {
        override val name = "entity"
    }
    data class TestEntity(val id: Long, val name: String)

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var introspector: JpqlRenderIntrospector


    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlEntity::class)
    }

    @ParameterizedTest
    @StatementClauseSource(
        excludes = [
            StatementClause(statement = JpqlRenderStatement.Select::class, clause = JpqlRenderClause.From::class),
            StatementClause(statement = JpqlRenderStatement.Update::class, clause = JpqlRenderClause.Update::class),
            StatementClause(statement = JpqlRenderStatement.Delete::class, clause = JpqlRenderClause.DeleteFrom::class),
        ],
    )
    fun `serialize - WHEN a combination of statement and clause is given as source, THEN draw only alias`(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Entities.entity(type = TestEntity::class, alias = "alias")
        val context = TestRenderContext(introspector, statement, clause)

        // when
        sut.serialize(part as JpqlEntity<*>, writer, context)

        // then
        verifySequence {
            writer.write(part.alias)
        }
    }

    @ParameterizedTest
    @StatementClauseSource(
        includes = [
            StatementClause(statement = JpqlRenderStatement.Select::class, clause = JpqlRenderClause.From::class),
            StatementClause(statement = JpqlRenderStatement.Update::class, clause = JpqlRenderClause.Update::class),
            StatementClause(statement = JpqlRenderStatement.Delete::class, clause = JpqlRenderClause.DeleteFrom::class),
        ],
    )
    fun `serialize - WHEN a combination of statement and clause is given as source, THEN draw full syntax`(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        every { writer.write(any<String>()) } just runs
        every { introspector.introspect(any()) } returns entityDescription

        val part = Entities.entity(type = TestEntity::class, alias = "alias")
        val context = TestRenderContext(introspector, statement, clause)

        // when
        sut.serialize(part as JpqlEntity<*>, writer, context)

        // then
        verifySequence {
            writer.write(entityDescription.name)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
            writer.write(part.alias)
        }
    }
}
