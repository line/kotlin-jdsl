package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlEntityType
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@JpqlSerializerTest
class JpqlEntityTypeSerializerTest : WithAssertions {
    private val sut = JpqlEntityTypeSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var serializer: JpqlRenderSerializer

    private val alias1 = "alias1"

    private val entity1 = Entities.entity(Employee::class, alias1)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlEntityType::class)
    }

    @Test
    fun serialize() {
        // given
        val part = Expressions.type(
            entity1,
        )
        val context = TestRenderContext(serializer)

        // when
        sut.serialize(part as JpqlEntityType, writer, context)

        // then
        verifySequence {
            writer.write("TYPE")
            writer.writeParentheses(any())
            serializer.serialize(entity1, writer, context)
        }
    }
}
