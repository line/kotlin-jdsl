//package com.linecorp.kotlinjdsl.dsl.jpql.expression
//
//import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
//import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
//import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
//import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
//import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
//import org.junit.jupiter.api.Test
//
//class CaseDslTest : AbstractJpqlDslTest() {
//    private val string1 = "string1"
//    private val nullableString1 = "nullableString1"
//
//    private val number1: Number = 1
//
//    private val int1 = 1
//    private val int2 = 2
//    private val nullableInt1: Int? = null
//    private val nullableInt2: Int? = null
//
//    @Test
//    fun `caseWhen predicate then int value`() {
//        // when
//        val expression = testJpql {
//            caseWhen(path(TestTable1::string1).equal(string1)).then(int1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.case(
//            whens = listOf(
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(int1),
//                ),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `caseWhen predicate then nullable int value`() {
//        // when
//        val expression = testJpql {
//            caseWhen(path(TestTable1::string1).equal(string1)).then(nullableInt1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.case(
//            whens = listOf(
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(nullableInt1),
//                ),
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `caseWhen predicate then int path`() {
//        // when
//        val expression = testJpql {
//            caseWhen(path(TestTable1::string1).equal(string1)).then(path(TestTable1::int1))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.case<Int>(
//            whens = listOf(
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Paths.path(TestTable1::int1),
//                ),
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `caseWhen predicate then ints`() {
//        // when
//        val expression = testJpql {
//            caseWhen(path(TestTable1::string1).equal(string1)).then(int1)
//                // more
//                .`when`(path(TestTable1::string1).equal(string1)).then(int1)
//                .`when`(path(TestTable1::string1).equal(string1)).then(nullableInt1)
//
//                .`when`(path(TestTable1::string1).equal(string1)).then(path(TestTable1::int1))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.case(
//            whens = listOf(
//                // first
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(int1),
//                ),
//                // more
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(int1),
//                ),
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(nullableInt1),
//                ),
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Paths.path(TestTable1::int1),
//                ),
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Paths.path(TestTable1::nullableInt1),
//                ),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `caseWhen predicate then numbers`() {
//        // when
//        val expression = testJpql {
//            caseWhen(path(TestTable1::string1).equal(string1)).then(number1)
//                // more
//                .`when`(path(TestTable1::string1).equal(string1)).then(int1)
//                .`when`(path(TestTable1::string1).equal(string1)).then(nullableInt1)
//
//                .`when`(path(TestTable1::string1).equal(string1)).then(path(TestTable1::int1))
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.case(
//            whens = listOf(
//                // first
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(int1),
//                ),
//                // more
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(int1),
//                ),
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(nullableInt1),
//                ),
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Paths.path(TestTable1::int1),
//                ),
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Paths.path(TestTable1::nullableInt1),
//                ),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `caseWhen predicate then int value else int value`() {
//        // when
//        val expression = testJpql {
//            caseWhen(path(TestTable1::string1).equal(string1)).then(int1)
//                .`else`(int2)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.case(
//            whens = listOf(
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(int1),
//                ),
//            ),
//            `else` = Expressions.value(int1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `caseWhen predicate then int value else nullable int value`() {
//        // when
//        val expression = testJpql {
//            caseWhen(path(TestTable1::string1).equal(string1)).then(int1)
//                .`else`(nullableInt2)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.case(
//            whens = listOf(
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(int1),
//                ),
//            ),
//            `else` = Expressions.value(nullableInt1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `caseWhen predicate then int value else int path`() {
//        // when
//        val expression = testJpql {
//            caseWhen(path(TestTable1::string1).equal(string1)).then(int1)
//                .`else`(path(TestTable1::int1))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.case(
//            whens = listOf(
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(int1),
//                ),
//            ),
//            `else` = Paths.path(TestTable1::int1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `caseWhen predicate then number value else int value`() {
//        // when
//        val expression = testJpql {
//            caseWhen(path(TestTable1::string1).equal(string1)).then(number1)
//                .`else`(int2)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.case(
//            whens = listOf(
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(int1),
//                ),
//            ),
//            `else` = Expressions.value(int1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `caseWhen predicate then number value else nullable int value`() {
//        // when
//        val expression = testJpql {
//            caseWhen(path(TestTable1::string1).equal(string1)).then(number1)
//                .`else`(nullableInt2)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.case(
//            whens = listOf(
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(int1),
//                ),
//            ),
//            `else` = Expressions.value(nullableInt1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `caseWhen predicate then number value else int path`() {
//        // when
//        val expression = testJpql {
//            caseWhen(path(TestTable1::string1).equal(string1)).then(number1)
//                .`else`(path(TestTable1::int1))
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.case(
//            whens = listOf(
//                Pair(
//                    Predicates.equal(
//                        value = Paths.path(TestTable1::string1),
//                        compareValue = Expressions.value(string1),
//                    ),
//                    Expressions.value(int1),
//                ),
//            ),
//            `else` = Paths.path(TestTable1::int1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when string value then int value`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(string1).then(int1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when string value then nullable int value`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(string1).then(nullableInt1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when nullable string value then int value`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(nullableString1).then(int1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when nullable string value then nullable int value`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(nullableString1).then(nullableInt1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when string value then int path`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(string1).then(path(TestTable1::int1))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when string value then nullable int path`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(string1).then(path(TestTable1::nullableInt1))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when nullable string value then int path`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(nullableString1).then(path(TestTable1::int1))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when nullable string value then nullable int path`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(nullableString1).then(path(TestTable1::nullableInt1))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when string path then int value`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(path(TestTable1::string2)).then(int1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when string path then nullable int value`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(path(TestTable1::string2)).then(nullableInt1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when string path then int path`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(path(TestTable1::string2)).then(path(TestTable1::int1))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when string path then nullable int path`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(path(TestTable1::string2)).then(path(TestTable1::nullableInt1))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when strings then ints`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                // first
//                .`when`(string1).then(int1)
//
//                // more
//                .`when`(string1).then(int1)
//                .`when`(string1).then(nullableInt1)
//                .`when`(string1).then(path(TestTable1::int1))
//                .`when`(string1).then(path(TestTable1::nullableInt1))
//
//                .`when`(nullableString1).then(int1)
//                .`when`(nullableString1).then(nullableInt1)
//                .`when`(nullableString1).then(path(TestTable1::int1))
//                .`when`(nullableString1).then(path(TestTable1::nullableInt1))
//
//                .`when`(path(TestTable1::string1)).then(int1)
//                .`when`(path(TestTable1::string1)).then(nullableInt1)
//                .`when`(path(TestTable1::string1)).then(path(TestTable1::int1))
//                .`when`(path(TestTable1::string1)).then(path(TestTable1::nullableInt1))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                // first
//                Expressions.value(string1) to Expressions.value(int1),
//                // more
//                Expressions.value(string1) to Expressions.value(int1),
//                Expressions.value(string1) to Expressions.value(nullableInt1),
//                Expressions.value(string1) to Paths.path(TestTable1::int1),
//                Expressions.value(string1) to Paths.path(TestTable1::nullableInt1),
//                Paths.path(TestTable1::string1) to Expressions.value(int1),
//                Paths.path(TestTable1::string1) to Expressions.value(nullableInt1),
//                Paths.path(TestTable1::string1) to Paths.path(TestTable1::int1),
//                Paths.path(TestTable1::string1) to Paths.path(TestTable1::nullableInt1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when strings then numbers`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                // first
//                .`when`(string1).then(number1)
//
//                // more
//                .`when`(string1).then(int1)
//                .`when`(string1).then(nullableInt1)
//                .`when`(string1).then(path(TestTable1::int1))
//                .`when`(string1).then(path(TestTable1::nullableInt1))
//
//                .`when`(nullableString1).then(int1)
//                .`when`(nullableString1).then(nullableInt1)
//                .`when`(nullableString1).then(path(TestTable1::int1))
//                .`when`(nullableString1).then(path(TestTable1::nullableInt1))
//
//                .`when`(path(TestTable1::string1)).then(int1)
//                .`when`(path(TestTable1::string1)).then(nullableInt1)
//                .`when`(path(TestTable1::string1)).then(path(TestTable1::int1))
//                .`when`(path(TestTable1::string1)).then(path(TestTable1::nullableInt1))
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                // first
//                Expressions.value(string1) to Expressions.value(int1),
//                // more
//                Expressions.value(string1) to Expressions.value(int1),
//                Expressions.value(string1) to Expressions.value(nullableInt1),
//                Expressions.value(string1) to Paths.path(TestTable1::int1),
//                Expressions.value(string1) to Paths.path(TestTable1::nullableInt1),
//                Paths.path(TestTable1::string1) to Expressions.value(int1),
//                Paths.path(TestTable1::string1) to Expressions.value(nullableInt1),
//                Paths.path(TestTable1::string1) to Paths.path(TestTable1::int1),
//                Paths.path(TestTable1::string1) to Paths.path(TestTable1::nullableInt1),
//            ),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when string value then int value else int value`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(string1).then(int1)
//                .`else`(int1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//            `else` = Expressions.value(int1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when value then value else nullable value`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(string1).then(int1)
//                .`else`(nullableInt1)
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//            `else` = Expressions.value(nullableInt1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when value then value else expression`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(string1).then(int1)
//                .`else`(path(TestTable1::int1))
//        }.toExpression()
//
//        val actual: Expression<Int> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//            `else` = Paths.path(TestTable1::int1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when number value then int value else int value`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(string1).then(number1)
//                .`else`(int1)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//            `else` = Expressions.value(int1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when string value then number value else nullable int value`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(string1).then(number1)
//                .`else`(nullableInt1)
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//            `else` = Expressions.value(nullableInt1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    @Test
//    fun `case string path when string value then number value else int path`() {
//        // when
//        val expression = testJpql {
//            case(path(TestTable1::string1))
//                .`when`(string1).then(number1)
//                .`else`(path(TestTable1::int1))
//        }.toExpression()
//
//        val actual: Expression<Number> = expression // for type check
//
//        // then
//        val expected = Expressions.caseValue(
//            value = Paths.path(TestTable1::string1),
//            whens = listOf(
//                Expressions.value(string1) to Expressions.value(int1),
//            ),
//            `else` = Paths.path(TestTable1::int1),
//        )
//
//        assertThat(actual.toExpression()).isEqualTo(expected)
//    }
//
//    private class TestTable1 {
//        val string1: String = "string1"
//        val string2: String = "string2"
//
//        val int1: Int = 1
//        val nullableInt1: Int? = null
//    }
//}
