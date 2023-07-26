//package com.linecorp.kotlinjdsl.dsl.jpql.select
//
//import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
//import com.linecorp.kotlinjdsl.dsl.jpql.select.impl.SelectQueryFromStepDsl
//import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
//import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
//import org.junit.jupiter.api.Test
//
//class SelectDslTest : AbstractJpqlDslTest() {
//    @Test
//    fun `select expression`() {
//        // when
//        val select = testJpql {
//            select(path(TestTable::int1))
//        }
//
//        val actual: SelectQueryFromStep<Int> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Int::class,
//            listOf(
//                Paths.path(TestTable::int1),
//            ),
//            false,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select nullable expression`() {
//        // when
//        val select = testJpql {
//            select(path(TestTable::nullableInt1))
//        }
//
//        val actual: SelectQueryFromStep<Int?> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int?>(
//            Int::class,
//            listOf(
//                Paths.path(TestTable::nullableInt1),
//            ),
//            false,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select expression distinct true`() {
//        // when
//        val select = testJpql {
//            select(path(TestTable::int1), distinct = true)
//        }
//
//        val actual: SelectQueryFromStep<Int> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Int::class,
//            listOf(
//                Paths.path(TestTable::int1),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select nullable expression distinct true`() {
//        // when
//        val select = testJpql {
//            select(path(TestTable::nullableInt1), distinct = true)
//        }
//
//        val actual: SelectQueryFromStep<Int?> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int?>(
//            Int::class,
//            listOf(
//                Paths.path(TestTable::nullableInt1),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type expression expression`() {
//        // when
//        val select = testJpql {
//            select<Row>(path(TestTable::int1), path(TestTable::int2))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::int2),
//            ),
//            false,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type expression nullable expression`() {
//        // when
//        val select = testJpql {
//            select<Row>(path(TestTable::int1), path(TestTable::nullableInt1))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::nullableInt1),
//            ),
//            false,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type nullable expression nullable expression`() {
//        // when
//        val select = testJpql {
//            select<Row>(path(TestTable::nullableInt1), path(TestTable::nullableInt2))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::nullableInt1),
//                Paths.path(TestTable::nullableInt2),
//            ),
//            false,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type collection expression expression`() {
//        // when
//        val select = testJpql {
//            select<Row>(listOf(path(TestTable::int1), path(TestTable::int2)))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::int2),
//            ),
//            false,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type collection expression nullable expression`() {
//        // when
//        val select = testJpql {
//            select<Row>(listOf(path(TestTable::int1), path(TestTable::nullableInt1)))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::nullableInt1),
//            ),
//            false,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type collection nullable expression nullable expression`() {
//        // when
//        val select = testJpql {
//            select<Row>(listOf(path(TestTable::nullableInt1), path(TestTable::nullableInt2)))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::nullableInt1),
//                Paths.path(TestTable::nullableInt2),
//            ),
//            false,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type expression expression distinct true`() {
//        // when
//        val select = testJpql {
//            select<Row>(path(TestTable::int1), path(TestTable::int2), distinct = true)
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::int2),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type expression nullable expression distinct true`() {
//        // when
//        val select = testJpql {
//            select<Row>(path(TestTable::int1), path(TestTable::nullableInt1), distinct = true)
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::nullableInt1),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type nullable expression nullable expression distinct true`() {
//        // when
//        val select = testJpql {
//            select<Row>(path(TestTable::nullableInt1), path(TestTable::nullableInt2), distinct = true)
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::nullableInt1),
//                Paths.path(TestTable::nullableInt2),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type collection expression expression distinct true`() {
//        // when
//        val select = testJpql {
//            select<Row>(listOf(path(TestTable::int1), path(TestTable::int2)), distinct = true)
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::int2),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type collection expression nullable expression distinct true`() {
//        // when
//        val select = testJpql {
//            select<Row>(listOf(path(TestTable::int1), path(TestTable::nullableInt1)), distinct = true)
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::nullableInt1),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `select type collection nullable expression nullable expression distinct true`() {
//        // when
//        val select = testJpql {
//            select<Row>(listOf(path(TestTable::nullableInt1), path(TestTable::nullableInt2)), distinct = true)
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::nullableInt1),
//                Paths.path(TestTable::nullableInt2),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `selectDistinct expression`() {
//        // when
//        val select = testJpql {
//            selectDistinct(path(TestTable::int1))
//        }
//
//        val actual: SelectQueryFromStep<Int> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Int::class,
//            listOf(
//                Paths.path(TestTable::int1),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `selectDistinct nullable expression`() {
//        // when
//        val select = testJpql {
//            selectDistinct(path(TestTable::nullableInt1))
//        }
//
//        val actual: SelectQueryFromStep<Int?> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int?>(
//            Int::class,
//            listOf(
//                Paths.path(TestTable::nullableInt1),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `selectDistinct type expression expression`() {
//        // when
//        val select = testJpql {
//            selectDistinct<Row>(path(TestTable::int1), path(TestTable::int2))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::int2),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `selectDistinct type expression nullable expression`() {
//        // when
//        val select = testJpql {
//            selectDistinct<Row>(path(TestTable::int1), path(TestTable::nullableInt1))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::nullableInt1),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `selectDistinct type nullable expression nullable expression`() {
//        // when
//        val select = testJpql {
//            selectDistinct<Row>(path(TestTable::nullableInt1), path(TestTable::nullableInt2))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::nullableInt1),
//                Paths.path(TestTable::nullableInt2),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `selectDistinct type collection expression expression`() {
//        // when
//        val select = testJpql {
//            selectDistinct<Row>(listOf(path(TestTable::int1), path(TestTable::int2)))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::int2),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `selectDistinct type collection expression nullable expression`() {
//        // when
//        val select = testJpql {
//            selectDistinct<Row>(listOf(path(TestTable::int1), path(TestTable::nullableInt1)))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::nullableInt1),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    @Test
//    fun `selectDistinct type collection nullable expression nullable expression`() {
//        // when
//        val select = testJpql {
//            selectDistinct<Row>(listOf(path(TestTable::nullableInt1), path(TestTable::nullableInt2)))
//        }
//
//        val actual: SelectQueryFromStep<Row> = select // for type check
//
//        // then
//        val expected = SelectQueryFromStepDsl<Int>(
//            Row::class,
//            listOf(
//                Paths.path(TestTable::nullableInt1),
//                Paths.path(TestTable::nullableInt2),
//            ),
//            true,
//        )
//
//        assertThat(actual)
//            .isNotInstanceOf(JpqlQueryable::class.java)
//            .isEqualTo(expected)
//    }
//
//    private class TestTable {
//        val int1: Int = 1
//        val int2: Int = 2
//
//        val nullableInt1: Int? = null
//        val nullableInt2: Int? = null
//    }
//
//    private class Row
//}
