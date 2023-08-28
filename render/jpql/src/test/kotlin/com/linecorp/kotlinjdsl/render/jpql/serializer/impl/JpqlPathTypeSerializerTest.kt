package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlPathType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlPathTypeSerializerTest : WithAssertions {
    private val sut = JpqlPathTypeSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val path1 = Paths.path(EmployeeDepartment::employee)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlPathType::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.type(
            path1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlPathType, writer, context)

        // then
        verifySequence {
            writer.write("TYPE")
            writer.writeParentheses(any())
            serializer.serialize(path1, writer, context)
        }
    }
}
