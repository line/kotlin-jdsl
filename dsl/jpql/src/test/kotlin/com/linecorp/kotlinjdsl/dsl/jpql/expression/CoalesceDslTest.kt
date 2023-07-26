//package com.linecorp.kotlinjdsl.dsl.jpql.expression
//
//import com.linecorp.kotlinjdsl.Experimental
//import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
//import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
//import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
//import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
//import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressionable
//import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
//import org.junit.jupiter.api.Test
//
//@Experimental
//class CoalesceDslTest : AbstractJpqlDslTest() {
//    private val number1: Number = 1
//
//    private val int1: Int = 1
//    private val int2: Int = 1
//    private val nullableInt1: Int? = null
//    private val nullableInt2: Int? = null
//
//    @Test
//    fun `coalesce int expression int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::int1), int1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce int expression nullable int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::int1), nullableInt1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(nullableInt1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce int expression nullable int value int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::int1), nullableInt1, int2)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(nullableInt1),
//            listOf(
//                Expressions.value(int2),
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce int expression nullable int value nullable int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::int1), nullableInt1, nullableInt2)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(nullableInt1),
//            listOf(
//                Expressions.value(nullableInt2)
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable int expression int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableInt1), int1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::nullableInt1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable int expression nullable int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableInt1), nullableInt1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable int expression nullable int value int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableInt1), nullableInt1, int2)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable int expression nullable int value nullable int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableInt1), nullableInt1, nullableInt2)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce int expression int value number value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::int1), int1, number1)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce int expression int expression number expression`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::int1), path(TestTable1::int1), path(TestTable1::number1))
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce number expression int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::number1), int1)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce number expression nullable int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::number1), nullableInt1)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce number expression nullable int value int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::number1), nullableInt1, int2)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce number expression nullable int value nullable int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::number1), nullableInt1, nullableInt2)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable number expression int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableNumber1), int1)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable number expression nullable int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableNumber1), nullableInt1)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable number expression nullable int value int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableNumber1), nullableInt1, int2)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable number expression nullable int value nullable int value`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableNumber1), nullableInt1, nullableInt2)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable int expression int expression`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableInt1), path(TestTable1::int2))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable int expression nullable int expression`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableInt1), path(TestTable1::nullableInt2))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable int expression nullable int expression int expression`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableInt1), path(TestTable1::nullableInt2), path(TestTable1::int3))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable int expression nullable int expression nullable int expression`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableInt1), path(TestTable1::nullableInt2), path(TestTable1::nullableInt3))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable number expression int expression`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableNumber1), path(TestTable1::int2))
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable number expression nullable int expression`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableNumber1), path(TestTable1::nullableInt2))
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable number expression nullable int expression int expression`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableNumber1), path(TestTable1::nullableInt2), path(TestTable1::int3))
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `coalesce nullable number expression nullable int expression nullable int expression`() {
//        // when
//        val expression = testJpql {
//            coalesce(path(TestTable1::nullableNumber1), path(TestTable1::nullableInt2), path(TestTable1::nullableInt3))
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.coalesce(
//            Paths.path(TestTable1::int1),
//            Expressions.value(int1),
//            emptyList(),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    private class TestTable1 {
//        val number1: Number = 1
//        val nullableNumber1: Number? = null
//
//        val int1: Int = 1
//        val int2: Int = 2
//        val int3: Int = 3
//        val nullableInt1: Int? = null
//        val nullableInt2: Int? = null
//        val nullableInt3: Int? = null
//    }
//}
