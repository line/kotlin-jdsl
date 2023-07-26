//package com.linecorp.kotlinjdsl.dsl.jpql.select
//
//import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
//import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
//import com.linecorp.kotlinjdsl.querymodel.jpql.delete.Deletes
//import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
//import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
//import org.junit.jupiter.api.Test
//
//class OrderByDslTest : AbstractJpqlDslTest() {
//    @Test
//    fun `orderBy expression asc`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).orderBy(
//                path(TestTable::int1).asc(),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select // for type check
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = null,
//            having = null,
//            orderBy = listOf(
//                Sorts.asc(Paths.path(TestTable::int1)),
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `orderBy nullable expression asc`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).orderBy(
//                path(TestTable::nullableInt1).asc(),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select // for type check
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = null,
//            having = null,
//            orderBy = listOf(
//                Sorts.asc(Paths.path(TestTable::nullableInt1)),
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `orderBy expression asc expression asc`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).orderBy(
//                path(TestTable::int1).asc(),
//                path(TestTable::int2).asc(),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select // for type check
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = null,
//            having = null,
//            orderBy = listOf(
//                Sorts.asc(Paths.path(TestTable::int1)),
//                Sorts.asc(Paths.path(TestTable::int2)),
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `orderBy expression asc nullable expression asc`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).orderBy(
//                path(TestTable::int1).asc(),
//                path(TestTable::nullableInt1).asc(),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select // for type check
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = null,
//            having = null,
//            orderBy = listOf(
//                Sorts.asc(Paths.path(TestTable::int1)),
//                Sorts.asc(Paths.path(TestTable::nullableInt1)),
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `orderBy nullable expression asc nullable expression asc`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).orderBy(
//                path(TestTable::nullableInt1).asc(),
//                path(TestTable::nullableInt2).asc(),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select // for type check
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = null,
//            having = null,
//            orderBy = listOf(
//                Sorts.asc(Paths.path(TestTable::nullableInt1)),
//                Sorts.asc(Paths.path(TestTable::nullableInt2)),
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `orderBy collection expression asc expression asc`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).orderBy(
//                listOf(
//                    path(TestTable::int1).asc(),
//                    path(TestTable::int2).asc(),
//                ),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select // for type check
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = null,
//            having = null,
//            orderBy = listOf(
//                Sorts.asc(Paths.path(TestTable::int1)),
//                Sorts.asc(Paths.path(TestTable::int2)),
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `orderBy collection expression asc nullable expression asc`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).orderBy(
//                listOf(
//                    path(TestTable::int1).asc(),
//                    path(TestTable::nullableInt1).asc(),
//                ),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select // for type check
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = null,
//            having = null,
//            orderBy = listOf(
//                Sorts.asc(Paths.path(TestTable::int1)),
//                Sorts.asc(Paths.path(TestTable::nullableInt1)),
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `orderBy collection nullable expression asc nullable expression asc`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).orderBy(
//                listOf(
//                    path(TestTable::nullableInt1).asc(),
//                    path(TestTable::nullableInt2).asc(),
//                ),
//            )
//        }.toQuery()
//
//        val actual: SelectQuery<TestTable> = select // for type check
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(Paths.entity(TestTable::class)),
//            where = null,
//            groupBy = null,
//            having = null,
//            orderBy = listOf(
//                Sorts.asc(Paths.path(TestTable::nullableInt1)),
//                Sorts.asc(Paths.path(TestTable::nullableInt2)),
//            ),
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
