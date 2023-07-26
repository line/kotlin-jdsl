//package com.linecorp.kotlinjdsl.dsl.jpql.select
//
//import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
//import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
//import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
//import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
//import com.linecorp.kotlinjdsl.querymodel.jpql.delete.Deletes
//import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
//import org.junit.jupiter.api.Test
//
//class HavingDslTest : AbstractJpqlDslTest() {
//    private val int1: Int = 1
//    private val int2: Int = 1
//
//    @Test
//    fun `having predicate`() {
//        // when
//        val select = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).having(
//                path(TestTable::int1).equal(int1),
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
//            from = listOf(
//                Paths.entity(TestTable::class),
//            ),
//            where = null,
//            groupBy = null,
//            having = Predicates.equal(
//                Paths.path(TestTable::int1),
//                Expressions.value(int1),
//            ),
//            orderBy = null,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `having and predicate`() {
//        // when
//        val select1 = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).havingAnd(
//                path(TestTable::int1).equal(int1),
//                path(TestTable::int1).equal(int2),
//            )
//        }.toQuery()
//
//        val actual1: SelectQuery<TestTable> = select1 // for type check
//
//        val select2 = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).havingAnd(
//                listOf(
//                    path(TestTable::int1).equal(int1),
//                    path(TestTable::int1).equal(int2),
//                ),
//            )
//        }.toQuery()
//
//        val actual2: SelectQuery<TestTable> = select2 // for type check
//
//        val select3 = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).having(
//                and(
//                    path(TestTable::int1).equal(int1),
//                    path(TestTable::int1).equal(int2),
//                ),
//            )
//        }.toQuery()
//
//        val actual3: SelectQuery<TestTable> = select3 // for type check
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(
//                Paths.entity(TestTable::class),
//            ),
//            where = null,
//            groupBy = null,
//            having = Predicates.and(
//                listOf(
//                    Predicates.equal(
//                        Paths.path(TestTable::int1),
//                        Expressions.value(int1),
//                    ),
//                    Predicates.equal(
//                        Paths.path(TestTable::int1),
//                        Expressions.value(int2),
//                    ),
//                ),
//            ),
//            orderBy = null,
//        )
//
//        assertThat(actual1).isEqualTo(expected)
//        assertThat(actual2).isEqualTo(expected)
//        assertThat(actual3).isEqualTo(expected)
//    }
//
//    @Test
//    fun `having or predicate`() {
//        // when
//        val select1 = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).havingOr(
//                path(TestTable::int1).equal(int1),
//                path(TestTable::int1).equal(int2),
//            )
//        }.toQuery()
//
//        val actual1: SelectQuery<TestTable> = select1 // for type check
//
//        val select2 = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).havingOr(
//                listOf(
//                    path(TestTable::int1).equal(int1),
//                    path(TestTable::int1).equal(int2),
//                ),
//            )
//        }.toQuery()
//
//        val actual2: SelectQuery<TestTable> = select2 // for type check
//
//        val select3 = testJpql {
//            select(
//                entity(TestTable::class),
//            ).from(
//                entity(TestTable::class),
//            ).having(
//                or(
//                    path(TestTable::int1).equal(int1),
//                    path(TestTable::int1).equal(int2),
//                ),
//            )
//        }.toQuery()
//
//        val actual3: SelectQuery<TestTable> = select3 // for type check
//
//        // then
//        val expected = Deletes.select<TestTable>(
//            returnType = TestTable::class,
//            select = listOf(Paths.entity(TestTable::class)),
//            distinct = false,
//            from = listOf(
//                Paths.entity(TestTable::class),
//            ),
//            where = null,
//            groupBy = null,
//            having = Predicates.or(
//                listOf(
//                    Predicates.equal(
//                        Paths.path(TestTable::int1),
//                        Expressions.value(int1),
//                    ),
//                    Predicates.equal(
//                        Paths.path(TestTable::int1),
//                        Expressions.value(int2),
//                    ),
//                ),
//            ),
//            orderBy = null,
//        )
//
//        assertThat(actual1).isEqualTo(expected)
//        assertThat(actual2).isEqualTo(expected)
//        assertThat(actual3).isEqualTo(expected)
//    }
//
//    private class TestTable {
//        val int1: Int = 1
//    }
//}
