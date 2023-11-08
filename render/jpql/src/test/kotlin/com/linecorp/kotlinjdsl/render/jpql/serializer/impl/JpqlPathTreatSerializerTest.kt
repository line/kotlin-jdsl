package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlPathTreat
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.render.jpql.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

@JpqlSerializerTest
class JpqlPathTreatSerializerTest : WithAssertions {
    private val sut = JpqlPathTreatSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var introspector: JpqlRenderIntrospector

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val entityDescription1 = object : JpqlEntityDescription {
        override val name = "entityName1"
    }

    private val path1 = Paths.path(EmployeeDepartment::employee)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlPathTreat::class)
    }

    @Test
    fun serialize() {
        // given
        every { introspector.introspect(any<KClass<*>>()) } returns entityDescription1

        val part = Paths.treat(
            path1,
            FullTimeEmployee::class,
        )
        val context = TestRenderContext(introspector, serializer)

        // when
        sut.serialize(part as JpqlPathTreat<*, *>, writer, context)

        // then
        verifySequence {
            introspector.introspect(FullTimeEmployee::class)
            writer.write("TREAT")
            writer.writeParentheses(any())
            serializer.serialize(path1, writer, context)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
            writer.write(entityDescription1.name)
        }
    }
}
