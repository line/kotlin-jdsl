package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlPathProperty
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Isbn
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
class JpqlPathPropertySerializerTest : WithAssertions {
    private val sut = JpqlPathPropertySerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var introspector: JpqlRenderIntrospector

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val path1 = Paths.path(Book::isbn)

    private val property1 = Isbn::value

    private val propertyDescription1 = object : JpqlPropertyDescription {
        override val name = property1.name
    }

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlPathProperty::class)
    }

    @Test
    fun serialize() {
        // given
        every { introspector.introspect(any<KCallable<*>>()) } returns propertyDescription1

        val part = Paths.path(
            path1,
            property1,
        )
        val context = TestRenderContext(introspector, serializer)

        // when
        sut.serialize(part as JpqlPathProperty<*, *>, writer, context)

        // then
        verifySequence {
            serializer.serialize(path1, writer, context)
            writer.write(".")
            writer.write(propertyDescription1.name)
        }
    }
}
