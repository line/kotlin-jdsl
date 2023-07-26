//package com.linecorp.kotlinjdsl.dsl.jpql.delete
//
//import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
//import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
//import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
//import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
//import com.linecorp.kotlinjdsl.querymodel.jpql.delete.Deletes
//import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
//import org.junit.jupiter.api.Test
//
//class WhereDslTest : AbstractJpqlDslTest() {
//    private val int1: Int = 1
//    private val int2: Int = 1
//
//    @Test
//    fun `where predicate`() {
//        // when
//        val delete = testJpql {
//            deleteFrom(
//                entity(TestTable::class),
//            ).where(
//                path(TestTable::int1).equal(int1),
//            )
//        }.toQuery()
//
//        val actual: DeleteQuery<TestTable> = delete // for type check
//
//        // then
//        val expected = Deletes.delete(
//            entity = Paths.entity(TestTable::class),
//            where = Predicates.equal(
//                value = Paths.path(TestTable::int1),
//                compareValue = Expressions.value(int1),
//            ),
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `where and predicate`() {
//        // when
//        val delete1 = testJpql {
//            deleteFrom(
//                entity(TestTable::class),
//            ).whereAnd(
//                path(TestTable::int1).equal(int1),
//                path(TestTable::int1).equal(int2),
//            )
//        }.toQuery()
//
//        val delete2 = testJpql {
//            deleteFrom(
//                entity(TestTable::class),
//            ).whereAnd(
//                listOf(
//                    path(TestTable::int1).equal(int1),
//                    path(TestTable::int1).equal(int2),
//                ),
//            )
//        }.toQuery()
//
//        val delete3 = testJpql {
//            deleteFrom(
//                entity(TestTable::class),
//            ).where(
//                and(
//                    path(TestTable::int1).equal(int1),
//                    path(TestTable::int1).equal(int2),
//                ),
//            )
//        }.toQuery()
//
//        val actual1: DeleteQuery<TestTable> = delete1 // for type check
//        val actual2: DeleteQuery<TestTable> = delete2 // for type check
//        val actual3: DeleteQuery<TestTable> = delete3 // for type check
//
//        // then
//        val expected = Deletes.delete(
//            entity = Paths.entity(TestTable::class),
//            where = Predicates.and(
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
//        )
//
//        assertThat(actual1).isEqualTo(expected)
//        assertThat(actual2).isEqualTo(expected)
//        assertThat(actual3).isEqualTo(expected)
//    }
//
//    @Test
//    fun `where or predicate`() {
//        // when
//        val delete1 = testJpql {
//            deleteFrom(
//                entity(TestTable::class),
//            ).whereOr(
//                path(TestTable::int1).equal(int1),
//                path(TestTable::int1).equal(int2),
//            )
//        }.toQuery()
//
//        val delete2 = testJpql {
//            deleteFrom(
//                entity(TestTable::class),
//            ).whereOr(
//                listOf(
//                    path(TestTable::int1).equal(int1),
//                    path(TestTable::int1).equal(int2),
//                ),
//            )
//        }.toQuery()
//
//        val delete3 = testJpql {
//            deleteFrom(
//                entity(TestTable::class),
//            ).where(
//                or(
//                    path(TestTable::int1).equal(int1),
//                    path(TestTable::int1).equal(int2),
//                ),
//            )
//        }.toQuery()
//
//        val actual1: DeleteQuery<TestTable> = delete1 // for type check
//        val actual2: DeleteQuery<TestTable> = delete2 // for type check
//        val actual3: DeleteQuery<TestTable> = delete3 // for type check
//
//        // then
//        val expected = Deletes.delete(
//            entity = Paths.entity(TestTable::class),
//            where = Predicates.or(
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
