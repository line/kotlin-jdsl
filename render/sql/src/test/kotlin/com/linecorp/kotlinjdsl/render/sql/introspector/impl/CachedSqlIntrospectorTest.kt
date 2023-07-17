package com.linecorp.kotlinjdsl.render.sql.introspector.impl

import com.linecorp.kotlinjdsl.render.sql.introspector.SqlColumnDescription
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlIntrospector
import com.linecorp.kotlinjdsl.render.sql.introspector.SqlTableDescription
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

@ExtendWith(MockKExtension::class)
class CachedSqlIntrospectorTest : WithAssertions {
    @MockK
    private lateinit var delegate: SqlIntrospector

    @MockK
    private lateinit var tableDescription: SqlTableDescription

    @MockK
    private lateinit var columnDescription: SqlColumnDescription

    @InjectMockKs
    private lateinit var sut: CachedSqlIntrospector

    @Test
    fun `if delegate-introspector returns null then introspect returns null`() {
        every { delegate.introspect(any<KClass<*>>()) } returns null
        every { delegate.introspect(any<KProperty1<*, *>>()) } returns null

        val clazz = mockk<KClass<*>>()
        val property = mockk<KProperty1<*, *>>()

        assertThat(sut.introspect(clazz)).isNull()
        assertThat(sut.introspect(property)).isNull()

        verify(exactly = 1) {
            delegate.introspect(clazz)
            delegate.introspect(property)
        }
    }

    @Test
    fun `if delegate-introspector returns sqlDescriptor introspect then returns introspect`() {
        data class Example(val test: Long)
        every { delegate.introspect(Example::class) } returns tableDescription
        every { delegate.introspect(Example::test) } returns columnDescription

        assertThat(sut.introspect(Example::class)).isEqualTo(tableDescription)
        assertThat(sut.introspect(Example::test)).isEqualTo(columnDescription)

        verify(exactly = 1) {
            delegate.introspect(Example::class)
            delegate.introspect(Example::test)
        }
    }
}
