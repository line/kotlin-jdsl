package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlId
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class JpqlIdSerializerTest {
    private val entity = Entities.entity(Book::class)

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var delegate: JpqlRenderSerializer

    private val context: TestRenderContext by lazy { TestRenderContext(delegate) }

    private val sut = JpqlIdSerializer()

    @BeforeEach
    fun setUp() {
        every { delegate.key } returns JpqlRenderSerializer.Key
        every { writer.write(any<String>()) } just Runs
        every { writer.writeParentheses(any()) } just Runs
        every { delegate.serialize(any(), any(), any()) } just Runs
    }

    @Test
    fun serialize() {
        // when
        sut.serialize(Expressions.id<Isbn>(entity) as JpqlId<*>, writer, context)

        // then
        verifySequence {
            writer.write("ID")
            writer.writeParentheses(any())
        }
    }
}
