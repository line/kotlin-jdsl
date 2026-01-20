package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlIn
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.entity.book.BookStatus
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.Calendar
import java.util.Date
import java.util.UUID

@JpqlSerializerTest
class JpqlInSerializerTest : WithAssertions {
    private val sut = JpqlInSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val expression1 = Paths.path(Book::price)
    private val expression2 = Expressions.value(BigDecimal.valueOf(100))
    private val expression3 = Expressions.value(BigDecimal.valueOf(200))
    private val expression4 = Expressions.value(BigDecimal.valueOf(300))

    @Test
    fun handledType() {
        // When
        val actual = sut.handledType()

        // Then
        assertThat(actual).isEqualTo(JpqlIn::class)
    }

    @Test
    fun `serialize() draws 0 = 1, when the compareValues is empty`() {
        // Given
        val part = Predicates.`in`(
            expression1,
            emptyList(),
        )
        val context = TestRenderContext(serializer)

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            writer.write("0 = 1")
        }
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are Number types`() {
        // Given
        val expressions = listOf(
            expression2,
            expression3,
            expression4,
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        val singleParam = Expressions.value(
            listOf(
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(200),
                BigDecimal.valueOf(300),
            ),
        )

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are String types`() {
        // Given
        val expression1 = Paths.path(Book::title)
        val expressions = listOf(
            Expressions.value("Book1"),
            Expressions.value("Book2"),
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        val singleParam = Expressions.value(
            listOf(
                "Book1",
                "Book2",
            ),
        )

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are Temporal types`() {
        // Given
        val expression1 = Paths.path(Book::publishDate)
        val date1 = OffsetDateTime.now()
        val date2 = OffsetDateTime.now().plusSeconds(1)

        val expressions = listOf(
            Expressions.value(date1),
            Expressions.value(date2),
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        val singleParam = Expressions.value(
            listOf(
                date1,
                date2,
            ),
        )

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are Enum types`() {
        // Given
        val expression1 = Paths.path(Book::status)
        val enum1 = BookStatus.IN_STOCK
        val enum2 = BookStatus.SOLD_OUT

        val expressions = listOf(
            Expressions.value(enum1),
            Expressions.value(enum2),
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        val singleParam = Expressions.value(
            listOf(
                enum1,
                enum2,
            ),
        )

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are Boolean types`() {
        // Given
        val expression1 = Paths.path(Book::hasEbook)
        val boolean1 = true
        val boolean2 = false

        val expressions = listOf(
            Expressions.value(boolean1),
            Expressions.value(boolean2),
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        val singleParam = Expressions.value(
            listOf(
                boolean1,
                boolean2,
            ),
        )

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are UUID types`() {
        // Given
        val expression1 = Paths.path(Book::uuidField)
        val uuid1 = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()

        val expressions = listOf(
            Expressions.value(uuid1),
            Expressions.value(uuid2),
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        val singleParam = Expressions.value(
            listOf(
                uuid1,
                uuid2,
            ),
        )

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are Date types`() {
        // Given
        val expression1 = Paths.path(Book::modifiedDate)
        val date1 = Date()
        val date2 = Date(date1.time + 1000)

        val expressions = listOf(
            Expressions.value(date1),
            Expressions.value(date2),
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        val singleParam = Expressions.value(
            listOf(
                date1,
                date2,
            ),
        )

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are Char types`() {
        // Given
        val expression1 = Paths.path(Book::firstLetter)
        val char1 = 'A'
        val char2 = 'B'

        val expressions = listOf(
            Expressions.value(char1),
            Expressions.value(char2),
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        val singleParam = Expressions.value(
            listOf(
                char1,
                char2,
            ),
        )

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are Calendar types`() {
        // Given
        val expression1 = Paths.path(Book::publicationCalendar)
        val calendar1 = Calendar.getInstance().apply { set(2020, Calendar.JANUARY, 1) }
        val calendar2 = Calendar.getInstance().apply { set(2021, Calendar.FEBRUARY, 15) }

        val expressions = listOf(
            Expressions.value(calendar1),
            Expressions.value(calendar2),
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        val singleParam = Expressions.value(
            listOf(
                calendar1,
                calendar2,
            ),
        )

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are ByteArray types`() {
        // Given
        val expression1 = Paths.path(Book::coverImageBytes)
        val bytes1 = byteArrayOf(1, 2, 3)
        val bytes2 = byteArrayOf(4, 5, 6)

        val expressions = listOf(
            Expressions.value(bytes1),
            Expressions.value(bytes2),
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        val singleParam = Expressions.value(
            listOf(
                bytes1,
                bytes2,
            ),
        )

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws a single parameter, when the compareValues are CharArray types`() {
        // Given
        val expression1 = Paths.path(Book::titleChars)
        val chars1 = charArrayOf('A', 'B', 'C')
        val chars2 = charArrayOf('D', 'E', 'F')

        val expressions = listOf(
            Expressions.value(chars1),
            Expressions.value(chars2),
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        val singleParam = Expressions.value(
            listOf(
                chars1,
                chars2,
            ),
        )

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            serializer.serialize(singleParam, writer, context)
        }
    }

    @Test
    fun `serialize() draws parameters, when the compareValues contain ByteArray and non-basic types`() {
        // Given
        val bytes = byteArrayOf(1, 2, 3)
        val expressions = listOf(
            Expressions.value(bytes),
            expression1,
        )

        val part = Predicates.`in`(
            Paths.path(Book::coverImageBytes),
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(Paths.path(Book::coverImageBytes), writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            writer.writeEach(expressions, ", ", any())
            serializer.serialize(Expressions.value(bytes), writer, context)
            serializer.serialize(expression1, writer, context)
        }
    }

    @Test
    fun `serialize() draws parameters, when the compareValues are not basic types`() {
        // Given
        val expressions = listOf(
            expression1,
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            writer.writeEach(expressions, ", ", any())
            serializer.serialize(expression1, writer, context)
        }
    }

    @Test
    fun `serialize() draws parameters, when the compareValues contain non-basic types`() {
        // Given
        val expressions = listOf(
            expression2,
            expression1,
        )

        val part = Predicates.`in`(
            expression1,
            compareValues = expressions,
        )
        val context = TestRenderContext(serializer)

        // When
        sut.serialize(part as JpqlIn<*>, writer, context)

        // Then
        verifySequence {
            serializer.serialize(expression1, writer, context)
            writer.write(" ")
            writer.write("IN")
            writer.write(" ")
            writer.writeParentheses(any())
            writer.writeEach(expressions, ", ", any())
            serializer.serialize(expression2, writer, context)
            serializer.serialize(expression1, writer, context)
        }
    }
}
