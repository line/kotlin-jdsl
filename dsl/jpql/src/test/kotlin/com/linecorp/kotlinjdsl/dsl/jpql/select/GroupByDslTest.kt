//package com.linecorp.kotlinjdsl.dsl.jpql.select
//
//import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
//import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
//import com.linecorp.kotlinjdsl.querymodel.jpql.delete.Deletes
//import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
//import org.junit.jupiter.api.Test
//
//class GroupByDslTest : AbstractJpqlDslTest() {
//    @Test
//    fun `groupBy expression`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).groupBy(
//                path(TestTable::int1),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = listOf(
//                Paths.path(TestTable::int1),
//            ),
//            having = null,
//            orderBy = null,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `groupBy nullable expression`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).groupBy(
//                path(TestTable::nullableInt1),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = listOf(
//                Paths.path(TestTable::nullableInt1),
//            ),
//            having = null,
//            orderBy = null,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `groupBy expression expression`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).groupBy(
//                path(TestTable::int1),
//                path(TestTable::int2),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::int2),
//            ),
//            having = null,
//            orderBy = null,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `groupBy expression nullable expression`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).groupBy(
//                path(TestTable::int1),
//                path(TestTable::nullableInt1),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::nullableInt1),
//            ),
//            having = null,
//            orderBy = null,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `groupBy nullable expression nullable expression`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).groupBy(
//                path(TestTable::nullableInt1),
//                path(TestTable::nullableInt2),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = listOf(
//                Paths.path(TestTable::nullableInt1),
//                Paths.path(TestTable::nullableInt2),
//            ),
//            having = null,
//            orderBy = null,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `groupBy collection expression expression`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).groupBy(
//                listOf(
//                    path(TestTable::int1),
//                    path(TestTable::int2),
//                ),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::int2),
//            ),
//            having = null,
//            orderBy = null,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `groupBy collection expression nullable expression`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).groupBy(
//                listOf(
//                    path(TestTable::int1),
//                    path(TestTable::nullableInt1),
//                ),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = listOf(
//                Paths.path(TestTable::int1),
//                Paths.path(TestTable::nullableInt1),
//            ),
//            having = null,
//            orderBy = null,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `groupBy collection nullable expression nullable expression`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).groupBy(
//                listOf(
//                    path(TestTable::nullableInt1),
//                    path(TestTable::nullableInt2),
//                ),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = listOf(
//                Paths.path(TestTable::nullableInt1),
//                Paths.path(TestTable::nullableInt2),
//            ),
//            having = null,
//            orderBy = null,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    private class TestTable {
//        val int1: Int = 1
//        val int2: Int = 1
//
//        val nullableInt1: Int? = null
//        val nullableInt2: Int? = null
//    }
//}
