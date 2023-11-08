package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

@JpqlSerializerTest
class JpqlValueSerializerTest : WithAssertions {
    private val sut = JpqlValueSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var introspector: JpqlRenderIntrospector

    private val value1 = "value1"

    private val entityDescription1 = object : JpqlEntityDescription {
        override val name = "entityName1"
    }

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlValue::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.value(
            value1,
        )
        val context = TestRenderContext()

        // when
        sut.serialize(part as JpqlValue<*>, writer, context)

        // then
        verifySequence {
            writer.writeParam(value1)
        }
    }

    @Test
    fun `serialize() draws entity name, when value is KClass`() {
        // given
        every { introspector.introspect(any<KClass<*>>()) } returns entityDescription1

        val part = Expressions.value(
            Book::class,
        )
        val context = TestRenderContext(introspector)

        // when
        sut.serialize(part as JpqlValue<*>, writer, context)

        // then
        verifySequence {
            introspector.introspect(Book::class)
            writer.write(entityDescription1.name)
        }
    }
}
