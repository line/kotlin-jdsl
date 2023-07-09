package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.*
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
        val expected = Case(
            whens = listOf(
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Param(null, int1),
                ),
            ),
            `else` = null,
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
        val expected = Case(
            whens = listOf(
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Param(null, nullableInt1),
                ),
            ),
            `else` = null,
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
        val expected = Case<Int>(
            whens = listOf(
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                ),
            ),
            `else` = null,
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
        val expected = Case<Int>(
            whens = listOf(
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                ),
            ),
            `else` = null,
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
        val expected = Case(
            whens = listOf(
                // first
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Param(null, int1),
                ),
                // more
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Param(null, int1),
                ),
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Param(null, nullableInt1),
                ),
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                ),
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                ),
            ),
            `else` = null,
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
        val expected = Case(
            whens = listOf(
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Param(null, int1),
                ),
            ),
            `else` = Param(null, int1),
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
        val expected = Case(
            whens = listOf(
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Param(null, int1),
                ),
            ),
            `else` = Param(null, nullableInt1),
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
        val expected = Case(
            whens = listOf(
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Param(null, int1),
                ),
            ),
            `else` = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `case when predicate then value else nullable expression`() {
        // when
        val expression = testJpql {
            caseWhen(path(TestTable::string1).equal(string1), int1).`else`(path(TestTable::nullableInt1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Case(
            whens = listOf(
                CaseWhen(
                    predicate = Equal(
                        left = Field(
                            AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                            TestTable::string1.name,
                        ),
                        right = Param(null, string1),
                        not = false,
                    ),
                    then = Param(null, int1),
                ),
            ),
            `else` = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
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
        val expected = CaseValue(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Param(null, int1),
                ),
            ),
            `else` = null,
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
        val expected = CaseValue(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Param(null, int1),
                ),
            ),
            `else` = null,
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
        val expected = CaseValue(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Param(null, nullableInt1),
                ),
            ),
            `else` = null,
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
        val expected = CaseValue<String, Int>(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                ),
            ),
            `else` = null,
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
        val expected = CaseValue<String, Int?>(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                ),
            ),
            `else` = null,
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
        val expected = CaseValue<String, Int>(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::string1.name,
                    ),
                    then = Param(null, int1),
                ),
            ),
            `else` = null,
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
        val expected = CaseValue<String, Int?>(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::string1.name,
                    ),
                    then = Param(null, nullableInt1),
                ),
            ),
            `else` = null,
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
        val expected = CaseValue<String, Int>(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::string1.name,
                    ),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                ),
            ),
            `else` = null,
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
        val expected = CaseValue<String, Int?>(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::string1.name,
                    ),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                ),
            ),
            `else` = null,
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
        val expected = CaseValue(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                // first
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Param(null, int1),
                ),
                // more
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Param(null, int1),
                ),
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Param(null, nullableInt1),
                ),
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                ),
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                ),
                CaseValueWhen(
                    compareValue = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::string1.name,
                    ),
                    then = Param(null, int1),
                ),
                CaseValueWhen(
                    compareValue = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::string1.name,
                    ),
                    then = Param(null, nullableInt1),
                ),
                CaseValueWhen(
                    compareValue = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::string1.name,
                    ),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::int1.name,
                    ),
                ),
                CaseValueWhen(
                    compareValue = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::string1.name,
                    ),
                    then = Field(
                        AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                        TestTable::nullableInt1.name,
                    ),
                ),
            ),
            `else` = null,
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
        val expected = CaseValue(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Param(null, int1),
                ),
            ),
            `else` = Param(null, int1),
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
        val expected = CaseValue(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Param(null, int1),
                ),
            ),
            `else` = Param(null, nullableInt1),
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
        val expected = CaseValue(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Param(null, int1),
                ),
            ),
            `else` = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::int1.name,
            ),
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

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = CaseValue(
            value = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::string1.name,
            ),
            whens = listOf(
                CaseValueWhen(
                    compareValue = Param(null, string1),
                    then = Param(null, int1),
                ),
            ),
            `else` = Field(
                AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                TestTable::nullableInt1.name,
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    private class TestTable {
        val string1: String = ""

        val int1: Int = 0

        val nullableInt1: Int = 0
    }
}
