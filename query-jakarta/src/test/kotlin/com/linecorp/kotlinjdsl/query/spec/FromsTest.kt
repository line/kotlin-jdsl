package com.linecorp.kotlinjdsl.query.spec

import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import jakarta.persistence.criteria.From
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Root

@ExtendWith(MockKExtension::class)
internal class FromsTest : WithKotlinJdslAssertions {
    @Test
    fun get() {
        // given
        val root: Root<*> = mockk()

        val entitySpec1 = EntitySpec(Data1::class.java)
        val from1: Path<*> = mockk()

        val entitySpec2 = EntitySpec(Data2::class.java)
        val from2: From<*, *> = mockk()

        val entitySpec3 = EntitySpec(Data3::class.java)

        // when
        val froms = Froms(
            root, mapOf(
                entitySpec1 to from1,
                entitySpec2 to from2,
            )
        )

        // then
        assertThat(froms[entitySpec1]).isEqualTo(from1)
        assertThat(froms[entitySpec2]).isEqualTo(from2)

        val exception = catchThrowable(IllegalStateException::class) { froms[entitySpec3] }
        assertThat(exception).isNotNull
    }

    @Test
    fun plus() {
        // given
        val rootOfLeft: Root<*> = mockk()
        val rootOfRight: Root<*> = mockk()

        val entitySpec1 = EntitySpec(Data1::class.java)
        val from1: From<*, *> = mockk()

        val entitySpec2 = EntitySpec(Data2::class.java)
        val from2: From<*, *> = mockk()

        val entitySpec3 = EntitySpec(Data2::class.java, "data2")
        val from3: From<*, *> = mockk()

        // when
        val leftFroms = Froms(
            rootOfLeft, mapOf(
                entitySpec1 to from1,
                entitySpec2 to from2,
            )
        )

        val rightFroms = Froms(
            rootOfRight, mapOf(
                entitySpec3 to from3,
            )
        )

        val actual = leftFroms + rightFroms

        // then
        assertThat(actual[entitySpec1]).isEqualTo(from1)
        assertThat(actual[entitySpec2]).isEqualTo(from2)
        assertThat(actual[entitySpec3]).isEqualTo(from3)
    }

    @Test
    fun `plus - if there is duplicated entity then throw exception`() {
        // given
        val rootOfLeft: Root<*> = mockk()
        val rootOfRight: Root<*> = mockk()

        val entitySpec1 = EntitySpec(Data1::class.java)
        val from1: From<*, *> = mockk()

        val entitySpec2 = EntitySpec(Data2::class.java)
        val from2: From<*, *> = mockk()

        val entitySpec3 = EntitySpec(Data2::class.java)
        val from3: From<*, *> = mockk()

        // when
        val leftFroms = Froms(
            rootOfLeft, mapOf(
                entitySpec1 to from1,
                entitySpec2 to from2,
            )
        )

        val rightFroms = Froms(
            rootOfRight, mapOf(
                entitySpec3 to from3,
            )
        )

        val exception = catchThrowable(IllegalStateException::class) {
            leftFroms + rightFroms
        }

        // then
        assertThat(exception)
            .hasMessageContaining("Other froms has duplicated entitySpec. Please alias the duplicated entities")
    }

    data class Data1(
        val id: Long
    )

    data class Data2(
        val id: Long
    )

    data class Data3(
        val id: Long
    )
}
