package com.linecorp.kotlinjdsl.querydsl.expression

import com.linecorp.kotlinjdsl.query.spec.expression.*
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ExpressionDslExtensionsTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var dsl: ExpressionDsl

    @Test
    fun col() {
        // given
        val columnSpec: ColumnSpec<String> = mockk()

        with(dsl) {
            every { column(EntitySpec(Data1::class.java), Data1::id) } returns columnSpec
        }

        // when
        val actual = with(dsl) { col(Data1::id) }

        // then
        assertThat(actual).isEqualTo(columnSpec)

        verify(exactly = 1) {
            with(dsl) { column(EntitySpec(Data1::class.java), Data1::id) }
        }

        confirmVerified(dsl)
    }

    @Test
    fun column() {
        // given
        val columnSpec: ColumnSpec<String> = mockk()

        with(dsl) {
            every { column(EntitySpec(Data1::class.java), Data1::id) } returns columnSpec
        }

        // when
        val actual = with(dsl) { column(Data1::id) }

        // then
        assertThat(actual).isEqualTo(columnSpec)

        verify(exactly = 1) {
            with(dsl) { column(EntitySpec(Data1::class.java), Data1::id) }
        }

        confirmVerified(dsl)
    }

    @Test
    fun nullLiteral() {
        // given
        val nullLiteral: NullLiteralSpec<String> = mockk()

        with(dsl) {
            every { nullLiteral(String::class.java) } returns nullLiteral
        }

        // when
        val actual = with(dsl) { nullLiteral<String>() }

        // then
        assertThat(actual).isEqualTo(nullLiteral)

        verify(exactly = 1) {
            with(dsl) { nullLiteral(String::class.java) }
        }

        confirmVerified(dsl)
    }

    @Test
    fun max() {
        // given
        val columnSpec: ColumnSpec<Int> = mockk()
        val maxSpec: MaxSpec<Int> = mockk()

        with(dsl) {
            every { column(EntitySpec(Data1::class.java), Data1::price) } returns columnSpec
            every { max(columnSpec) } returns maxSpec
        }

        // when
        val actual = with(dsl) { max(Data1::price) }

        // then
        assertThat(actual).isEqualTo(maxSpec)

        verify(exactly = 1) {
            with(dsl) {
                column(EntitySpec(Data1::class.java), Data1::price)
                max(columnSpec)
            }
        }

        confirmVerified(dsl)
    }

    @Test
    fun min() {
        // given
        val columnSpec: ColumnSpec<Int> = mockk()
        val minSpec: MinSpec<Int> = mockk()

        with(dsl) {
            every { column(EntitySpec(Data1::class.java), Data1::price) } returns columnSpec
            every { min(columnSpec) } returns minSpec
        }

        // when
        val actual = with(dsl) { min(Data1::price) }

        // then
        assertThat(actual).isEqualTo(minSpec)

        verify(exactly = 1) {
            with(dsl) {
                column(EntitySpec(Data1::class.java), Data1::price)
                min(columnSpec)
            }
        }

        confirmVerified(dsl)
    }

    @Test
    fun avg() {
        // given
        val columnSpec: ColumnSpec<Int> = mockk()
        val avgSpec: AvgSpec<Int> = mockk()

        with(dsl) {
            every { column(EntitySpec(Data1::class.java), Data1::price) } returns columnSpec
            every { avg(columnSpec) } returns avgSpec
        }

        // when
        val actual = with(dsl) { avg(Data1::price) }

        // then
        assertThat(actual).isEqualTo(avgSpec)

        verify(exactly = 1) {
            with(dsl) {
                column(EntitySpec(Data1::class.java), Data1::price)
                avg(columnSpec)
            }
        }

        confirmVerified(dsl)
    }

    @Test
    fun sum() {
        // given
        val columnSpec: ColumnSpec<Int> = mockk()
        val sumSpec: SumSpec<Int> = mockk()

        with(dsl) {
            every { column(EntitySpec(Data1::class.java), Data1::price) } returns columnSpec
            every { sum(columnSpec) } returns sumSpec
        }

        // when
        val actual = with(dsl) { sum(Data1::price) }

        // then
        assertThat(actual).isEqualTo(sumSpec)

        verify(exactly = 1) {
            with(dsl) {
                column(EntitySpec(Data1::class.java), Data1::price)
                sum(columnSpec)
            }
        }

        confirmVerified(dsl)
    }

    @Test
    fun count() {
        // given
        val columnSpec: ColumnSpec<Int> = mockk()
        val countSpec: CountSpec<Int> = mockk()

        with(dsl) {
            every { column(EntitySpec(Data1::class.java), Data1::price) } returns columnSpec
            every { count(true, columnSpec) } returns countSpec
        }

        // when
        val actual = with(dsl) { count(distinct = true, Data1::price) }

        // then
        assertThat(actual).isEqualTo(countSpec)

        verify(exactly = 1) {
            with(dsl) {
                column(EntitySpec(Data1::class.java), Data1::price)
                count(true, columnSpec)
            }
        }

        confirmVerified(dsl)
    }

    @Test
    fun countNonDistinct() {
        // given
        val columnSpec: ColumnSpec<Int> = mockk()
        val countSpec: CountSpec<Int> = mockk()

        with(dsl) {
            every { column(EntitySpec(Data1::class.java), Data1::price) } returns columnSpec
            every { count(columnSpec) } returns countSpec
        }

        // when
        val actual = with(dsl) { count(Data1::price) }

        // then
        assertThat(actual).isEqualTo(countSpec)

        verify(exactly = 1) {
            with(dsl) {
                column(EntitySpec(Data1::class.java), Data1::price)
                count(columnSpec)
            }
        }

        confirmVerified(dsl)
    }

    @Test
    fun countDistinct() {
        // given
        val columnSpec: ColumnSpec<Int> = mockk()
        val countSpec: CountSpec<Int> = mockk()

        with(dsl) {
            every { column(EntitySpec(Data1::class.java), Data1::price) } returns columnSpec
            every { countDistinct(columnSpec) } returns countSpec
        }

        // when
        val actual = with(dsl) { countDistinct(Data1::price) }

        // then
        assertThat(actual).isEqualTo(countSpec)

        verify(exactly = 1) {
            with(dsl) {
                column(EntitySpec(Data1::class.java), Data1::price)
                countDistinct(columnSpec)
            }
        }

        confirmVerified(dsl)
    }

    @Test
    fun greatest() {
        // given
        val columnSpec: ColumnSpec<Int> = mockk()
        val greatestSpec: GreatestSpec<Int> = mockk()

        with(dsl) {
            every { column(EntitySpec(Data1::class.java), Data1::price) } returns columnSpec
            every { greatest(columnSpec) } returns greatestSpec
        }

        // when
        val actual = with(dsl) { greatest(Data1::price) }

        // then
        assertThat(actual).isEqualTo(greatestSpec)

        verify(exactly = 1) {
            with(dsl) {
                column(EntitySpec(Data1::class.java), Data1::price)
                greatest(columnSpec)
            }
        }

        confirmVerified(dsl)
    }

    @Test
    fun least() {
        // given
        val columnSpec: ColumnSpec<Int> = mockk()
        val leastSpec: LeastSpec<Int> = mockk()

        with(dsl) {
            every { column(EntitySpec(Data1::class.java), Data1::price) } returns columnSpec
            every { least(columnSpec) } returns leastSpec
        }

        // when
        val actual = with(dsl) { least(Data1::price) }

        // then
        assertThat(actual).isEqualTo(leastSpec)

        verify(exactly = 1) {
            with(dsl) {
                column(EntitySpec(Data1::class.java), Data1::price)
                least(columnSpec)
            }
        }

        confirmVerified(dsl)
    }

    @Test
    fun functionVarArg() {
        // given
        val columnSpec: ColumnSpec<String> = mockk()
        val literal1: LiteralSpec<Int> = mockk()
        val literal2: LiteralSpec<Int> = mockk()
        val functionSpec: FunctionSpec<String> = mockk()
        val entitySpec = EntitySpec(Data1::class.java)

        with(dsl) {
            every { literal(1) } returns literal1
            every { literal(2) } returns literal2
            every { column(entitySpec, Data1::id) } returns columnSpec
            every { function<String>("substring", listOf(columnSpec, literal1, literal2)) } returns functionSpec
        }

        // when
        val actual = with(dsl) { function<String>("substring", column(entitySpec, Data1::id), literal(1), literal(2)) }

        // then
        assertThat(actual).isEqualTo(functionSpec)

        verify(exactly = 1) {
            with(dsl) {
                literal(1)
                literal(2)
                column(entitySpec, Data1::id)
                function<String>("substring", listOf(columnSpec, literal1, literal2))
            }
        }

        confirmVerified(dsl)
    }

    @Test
    fun functionList() {
        // given
        val columnSpec: ColumnSpec<String> = mockk()
        val literal1: LiteralSpec<Int> = mockk()
        val literal2: LiteralSpec<Int> = mockk()
        val functionSpec: FunctionSpec<String> = mockk()
        val entitySpec = EntitySpec(Data1::class.java)

        with(dsl) {
            every { literal(1) } returns literal1
            every { literal(2) } returns literal2
            every { column(entitySpec, Data1::id) } returns columnSpec
            every {
                function(
                    "substring",
                    String::class.java,
                    listOf(columnSpec, literal1, literal2)
                )
            } returns functionSpec
        }

        // when
        val actual =
            with(dsl) { function<String>("substring", listOf(column(entitySpec, Data1::id), literal(1), literal(2))) }

        // then
        assertThat(actual).isEqualTo(functionSpec)

        verify(exactly = 1) {
            with(dsl) {
                literal(1)
                literal(2)
                column(entitySpec, Data1::id)
                function(
                    "substring",
                    String::class.java,
                    listOf(columnSpec, literal1, literal2)
                )
            }
        }

        confirmVerified(dsl)
    }

    class Data1 {
        val id: String = "10"
        val price: Int = 100
    }
}
