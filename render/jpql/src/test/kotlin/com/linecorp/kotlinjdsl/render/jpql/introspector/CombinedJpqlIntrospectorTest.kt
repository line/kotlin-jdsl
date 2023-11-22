package com.linecorp.kotlinjdsl.render.jpql.introspector

import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

@ExtendWith(MockKExtension::class)
class CombinedJpqlIntrospectorTest : WithAssertions {
    private lateinit var sut: CombinedJpqlIntrospector

    @MockK
    private lateinit var introspector1: JpqlIntrospector

    @MockK
    private lateinit var introspector2: JpqlIntrospector

    private val entityDescription1 = object : JpqlEntityDescription {
        override val name: String = "entityName1"
    }

    private val propertyDescription1 = object : JpqlPropertyDescription {
        override val name: String = "propertyName1"
    }

    @BeforeEach
    fun setUp() {
        sut = CombinedJpqlIntrospector(
            introspector1,
            introspector2,
        )
    }

    @Test
    fun `introspect(type) return the description of the primary, when the primary returns non null`() {
        // given
        every { introspector1.introspect(any<KClass<*>>()) } returns entityDescription1

        // when
        val actual = sut.introspect(Book::class)

        // then
        assertThat(actual).isEqualTo(entityDescription1)

        verifySequence {
            introspector1.introspect(Book::class)
        }
    }

    @Test
    fun `introspect(type) return the description of the secondary, when the primary returns null`() {
        // given
        every { introspector1.introspect(any<KClass<*>>()) } returns null
        every { introspector2.introspect(any<KClass<*>>()) } returns entityDescription1

        // when
        val actual = sut.introspect(Book::class)

        // then
        assertThat(actual).isEqualTo(entityDescription1)

        verifySequence {
            introspector1.introspect(Book::class)
            introspector2.introspect(Book::class)
        }
    }

    @Test
    fun `introspect(property) return the description of the primary, when the primary returns non null`() {
        // given
        every { introspector1.introspect(any<KCallable<*>>()) } returns propertyDescription1

        // when
        val actual = sut.introspect(Book::title)

        // then
        assertThat(actual).isEqualTo(propertyDescription1)

        verifySequence {
            introspector1.introspect(Book::title)
        }
    }

    @Test
    fun `introspect(property) return the description of the secondary, when the primary returns null`() {
        // given
        every { introspector1.introspect(any<KCallable<*>>()) } returns null
        every { introspector2.introspect(any<KCallable<*>>()) } returns propertyDescription1

        // when
        val actual = sut.introspect(Book::title)

        // then
        assertThat(actual).isEqualTo(propertyDescription1)

        verifySequence {
            introspector1.introspect(Book::title)
            introspector2.introspect(Book::title)
        }
    }
}
