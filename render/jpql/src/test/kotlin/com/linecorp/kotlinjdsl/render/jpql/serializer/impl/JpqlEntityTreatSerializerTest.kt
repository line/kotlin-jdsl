package com.linecorp.kotlinjdsl.render.jpql.serializer.impl

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.impl.JpqlEntityTreat
import com.linecorp.kotlinjdsl.render.TestRenderContext
import com.linecorp.kotlinjdsl.render.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.render.jpql.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderStatement
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerTest
import com.linecorp.kotlinjdsl.render.jpql.serializer.StatementClause
import com.linecorp.kotlinjdsl.render.jpql.serializer.StatementClauseSource
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import kotlin.reflect.KClass

@JpqlSerializerTest
class JpqlEntityTreatSerializerTest : WithAssertions {
    private val sut = JpqlEntityTreatSerializer()

    @MockK
    private lateinit var writer: JpqlWriter

    @MockK
    private lateinit var introspector: JpqlRenderIntrospector

    private val entityDescription1 = object : JpqlEntityDescription {
        override val name = "entityName1"
    }

    private val alias1 = "alias1"

    private val entity1 = Entities.entity(Employee::class, alias1)

    @Test
    fun handledType() {
        // when
        val actual = sut.handledType()

        // then
        assertThat(actual).isEqualTo(JpqlEntityTreat::class)
    }

    @ParameterizedTest
    @StatementClauseSource(
        excludes = [
            StatementClause(JpqlRenderStatement.Select::class, JpqlRenderClause.From::class),
        ],
    )
    fun serialize(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        every { introspector.introspect(any<KClass<*>>()) } returns entityDescription1

        val part = Entities.treat(
            entity1,
            FullTimeEmployee::class,
        )
        val context = TestRenderContext(introspector, statement, clause)

        // when
        sut.serialize(part as JpqlEntityTreat<*, *>, writer, context)

        // then
        verifySequence {
            introspector.introspect(FullTimeEmployee::class)
            writer.write("TREAT")
            writer.writeParentheses(any())
            writer.write(alias1)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
            writer.write(entityDescription1.name)
        }
    }

    @ParameterizedTest
    @StatementClauseSource(
        includes = [
            StatementClause(JpqlRenderStatement.Select::class, JpqlRenderClause.From::class),
        ],
    )
    fun `serialize() does not draw treat, when given the statement and clause of the source`(
        statement: JpqlRenderStatement,
        clause: JpqlRenderClause,
    ) {
        // given
        every { introspector.introspect(any<KClass<*>>()) } returns entityDescription1

        val part = Entities.treat(
            entity1,
            FullTimeEmployee::class,
        )
        val context = TestRenderContext(introspector, statement, clause)

        // when
        sut.serialize(part as JpqlEntityTreat<*, *>, writer, context)

        // then
        verifySequence {
            introspector.introspect(FullTimeEmployee::class)
            writer.write(entityDescription1.name)
            writer.write(" ")
            writer.write("AS")
            writer.write(" ")
            writer.write(alias1)
        }
    }
}
