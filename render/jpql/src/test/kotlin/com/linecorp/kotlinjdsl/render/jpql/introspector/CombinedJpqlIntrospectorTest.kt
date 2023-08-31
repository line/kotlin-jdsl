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

    @BeforeEach
    fun setUp() {
        sut = CombinedJpqlIntrospector(
            introspector1,
            introspector2,
        )
    }

    @Test
    fun `introspect() return the description of the primary, when the primary returns non null`() {
        // given
        every { introspector1.introspect(any()) } returns entityDescription1

        // when
        val actual = sut.introspect(Book::class)

        // then
        assertThat(actual).isEqualTo(entityDescription1)

        verifySequence {
            introspector1.introspect(Book::class)
        }
    }

    @Test
    fun `introspect() return the description of the secondary, when the primary returns null`() {
        // given
        every { introspector1.introspect(any()) } returns null
        every { introspector2.introspect(any()) } returns entityDescription1

        // when
        val actual = sut.introspect(Book::class)

        // then
        assertThat(actual).isEqualTo(entityDescription1)

        verifySequence {
            introspector1.introspect(Book::class)
            introspector2.introspect(Book::class)
        }
    }
}
