package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlEntityProperty
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlPropertyDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import kotlin.reflect.KCallable

@JpqlSerializerTest
class JpqlEntityPropertySerializerTest : WithAssertions {
    private val sut = JpqlEntityPropertySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var introspector: JpqlRenderIntrospector

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val entity1 = Entities.entity(Book::class, "book01")
    private val property1 = Book::price
    private val propertyDescription1 = object : JpqlPropertyDescription {
        override val name = property1.name
    }

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlEntityProperty::class)
    }

    @Test
    fun serialize() {
        // given
        every { introspector.introspect(any<KCallable<*>>()) } returns propertyDescription1

        val part = Paths.path(
            entity1,
            property1,
        )
        val context = TestRenderContext(introspector, serializer)

        // when
        sut.serialize(part as JpqlEntityProperty<*, *>, writer, context)

        // then
        verifySequence {
            writer.write(entity1.alias)
            writer.write(".")
            writer.write(propertyDescription1.name)
        }
    }
}
