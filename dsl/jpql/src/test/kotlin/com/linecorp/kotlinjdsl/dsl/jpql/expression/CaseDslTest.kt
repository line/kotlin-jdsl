package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import org.junit.jupiter.api.Test

class CaseDslTest : AbstractJpqlDslTest() {
    private val string1 = "string1"

    private val int1 = 1

    private val nullableInt1: Int? = null

    @Test
    fun `case when predicate then value`() {
        // when
        val expression = testJpql {
            caseWhen(path(TestTable::string1).equal(string1), int1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.case(
            whens = listOf(
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Expressions.value(int1),
                ),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then nullable value`() {
        // when
        val expression = testJpql {
            caseWhen(path(TestTable::string1).equal(string1), nullableInt1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.case(
            whens = listOf(
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Expressions.value(nullableInt1),
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then expression`() {
        // when
        val expression = testJpql {
            caseWhen(path(TestTable::string1).equal(string1), path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.case<Int>(
            whens = listOf(
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Paths.path(TestTable::int1),
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then nullable expression`() {
        // when
        val expression = testJpql {
            caseWhen(path(TestTable::string1).equal(string1), path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.case(
            whens = listOf(
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Paths.path(TestTable::nullableInt1),
                ),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case when more`() {
        // when
        val expression = testJpql {
            caseWhen(path(TestTable::string1).equal(string1), value(int1))
                .`when`(path(TestTable::string1).equal(string1), value(int1))
                .`when`(path(TestTable::string1).equal(string1), value(nullableInt1))

                .`when`(path(TestTable::string1).equal(string1), path(TestTable::int1))
                .`when`(path(TestTable::string1).equal(string1), path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.case(
            whens = listOf(
                // first
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Expressions.value(int1),
                ),
                // more
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Expressions.value(int1),
                ),
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Expressions.value(nullableInt1),
                ),
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Paths.path(TestTable::int1),
                ),
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Paths.path(TestTable::nullableInt1),
                ),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then value else value`() {
        // when
        val expression = testJpql {
            caseWhen(path(TestTable::string1).equal(string1), int1).`else`(int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.case(
            whens = listOf(
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Expressions.value(int1),
                ),
            ),
            `else` = Expressions.value(int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then value else nullable value`() {
        // when
        val expression = testJpql {
            caseWhen(path(TestTable::string1).equal(string1), int1).`else`(nullableInt1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.case(
            whens = listOf(
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Expressions.value(int1),
                ),
            ),
            `else` = Expressions.value(nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then value else expression`() {
        // when
        val expression = testJpql {
            caseWhen(path(TestTable::string1).equal(string1), int1).`else`(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.case(
            whens = listOf(
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Expressions.value(int1),
                ),
            ),
            `else` = Paths.path(TestTable::int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then value else nullable expression`() {
        // when
        val expression = testJpql {
            caseWhen(path(TestTable::string1).equal(string1), int1).`else`(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.case(
            whens = listOf(
                Pair(
                    Predicates.equal(
                        value = Paths.path(TestTable::string1),
                        compareValue = Expressions.value(string1),
                    ),
                    Expressions.value(int1),
                ),
            ),
            `else` = Paths.path(TestTable::nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1)).`when`(string1, int1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Expressions.value(string1) to Expressions.value(int1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when first value then value`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1)).`when`(string1, int1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Expressions.value(string1) to Expressions.value(int1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when first value then nullable value`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1)).`when`(string1, nullableInt1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Expressions.value(string1) to Expressions.value(nullableInt1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when first value then expression`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1)).`when`(string1, path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Expressions.value(string1) to Paths.path(TestTable::int1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when first value then nullable expression`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1)).`when`(string1, path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Expressions.value(string1) to Paths.path(TestTable::nullableInt1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when first expression then value`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1)).`when`(path(TestTable::string1), int1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Paths.path(TestTable::string1) to Expressions.value(int1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when first expression then nullable value`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1)).`when`(path(TestTable::string1), nullableInt1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Paths.path(TestTable::string1) to Expressions.value(nullableInt1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when first expression then expression`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1)).`when`(path(TestTable::string1), path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Paths.path(TestTable::string1) to Paths.path(TestTable::int1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when first expression then nullable expression`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1)).`when`(path(TestTable::string1), path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Paths.path(TestTable::string1) to Paths.path(TestTable::nullableInt1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when more`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1))
                // first
                .`when`(string1, int1)

                // more
                .`when`(string1, int1)
                .`when`(string1, nullableInt1)
                .`when`(string1, path(TestTable::int1))
                .`when`(string1, path(TestTable::nullableInt1))

                .`when`(path(TestTable::string1), int1)
                .`when`(path(TestTable::string1), nullableInt1)
                .`when`(path(TestTable::string1), path(TestTable::int1))
                .`when`(path(TestTable::string1), path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                // first
                Expressions.value(string1) to Expressions.value(int1),
                // more
                Expressions.value(string1) to Expressions.value(int1),
                Expressions.value(string1) to Expressions.value(nullableInt1),
                Expressions.value(string1) to Paths.path(TestTable::int1),
                Expressions.value(string1) to Paths.path(TestTable::nullableInt1),
                Paths.path(TestTable::string1) to Expressions.value(int1),
                Paths.path(TestTable::string1) to Expressions.value(nullableInt1),
                Paths.path(TestTable::string1) to Paths.path(TestTable::int1),
                Paths.path(TestTable::string1) to Paths.path(TestTable::nullableInt1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when value then value else value`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1))
                .`when`(string1, int1)
                .`else`(int1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Expressions.value(string1) to Expressions.value(int1),
            ),
            `else` = Expressions.value(int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when value then value else nullable value`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1))
                .`when`(string1, int1)
                .`else`(nullableInt1)
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Expressions.value(string1) to Expressions.value(int1),
            ),
            `else` = Expressions.value(nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when value then value else expression`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1))
                .`when`(string1, int1)
                .`else`(path(TestTable::int1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Expressions.value(string1) to Expressions.value(int1),
            ),
            `else` = Paths.path(TestTable::int1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case expression when value then value else nullable expression`() {
        // when
        val expression = testJpql {
            case(path(TestTable::string1))
                .`when`(string1, int1)
                .`else`(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int?> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = Paths.path(TestTable::string1),
            whens = listOf(
                Expressions.value(string1) to Expressions.value(int1),
            ),
            `else` = Paths.path(TestTable::nullableInt1),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    private class TestTable {
        val string1: String = ""

        val int1: Int = 0
        val nullableInt1: Int? = null
    }
}
