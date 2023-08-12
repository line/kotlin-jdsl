package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntity
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test


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

    @Test
    fun `serialize - WHEN statement is select and clause is not select, THEN draw only alias`() {
        // given
        every { writer.write(any<String>()) } just runs

        val part = Entities.entity(type = TestEntity::class, alias = "alias")
        val context = TestRenderContext(introspector, JpqlRenderStatement.Select, JpqlRenderClause.DeleteFrom)

        // when
        sut.serialize(part as JpqlEntity<*>, writer, context)

        // then
        verifySequence {
            writer.write(part.alias)
        }
    }

    @Test
    fun `serialize - WHEN statement is select and clause is from, THEN draw entity name with alias`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { introspector.introspect(any()) } returns entityDescription

        val part = Entities.entity(type = TestEntity::class, alias = "alias")
        val context = TestRenderContext(introspector, JpqlRenderStatement.Select, JpqlRenderClause.From)

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

    @Test
    fun `serialize - WHEN statement is update and clause is update, THEN draw entity name with alias`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { introspector.introspect(any()) } returns entityDescription

        val part = Entities.entity(type = TestEntity::class, alias = "alias")
        val context = TestRenderContext(introspector, JpqlRenderStatement.Update, JpqlRenderClause.Update)

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

    @Test
    fun `serialize - WHEN statement is delete and clause is delete, THEN draw entity name with alias`() {
        // given
        every { writer.write(any<String>()) } just runs
        every { introspector.introspect(any()) } returns entityDescription

        val part = Entities.entity(type = TestEntity::class, alias = "alias")
        val context = TestRenderContext(introspector, JpqlRenderStatement.Delete, JpqlRenderClause.DeleteFrom)

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
