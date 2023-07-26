//package com.linecorp.kotlinjdsl.dsl.jpql.expression
//
//import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
//import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
//import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
//import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
//import org.junit.jupiter.api.Test
//
//class AliasDslTest : AbstractJpqlDslTest() {
//    private val alias1 = "alias1"
//    private val alias2 = "alias2"
//
//    @Test
//    fun `expression as string`() {
//        // when
//        val expression = testJpql {
//            count(path(TestTable1::int1)).`as`(alias1)
//        }
//
//        val actual: Expression<Long> = expression // for type check
//
//        // then
//        val expected = Expressions.alias(
//            Expressions.count(
//                Paths.path(TestTable1::int1),
//                distinct = false,
//            ),
//            alias1,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `expression alias string`() {
//        // when
//        val expression = testJpql {
//            count(path(TestTable1::int1)).alias(alias1)
//        }
//
//        val actual: Expression<Long> = expression // for type check
//
//        // then
//        val expected = Expressions.alias(
//            Expressions.count(
//                Paths.path(TestTable1::int1),
//                distinct = false,
//            ),
//            alias1,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `expression as string as string`() {
//        // when
//        val expression = testJpql {
//            count(path(TestTable1::int1)).`as`(alias1).`as`(alias2)
//        }
//
//        val actual: Expression<Long> = expression // for type check
//
//        // then
//        val expected = Expressions.alias(
//            Expressions.count(
//                Paths.path(TestTable1::int1),
//                distinct = false,
//            ),
//            alias2,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    @Test
//    fun `expression alias string alias string`() {
//        // when
//        val expression = testJpql {
//            count(path(TestTable1::int1)).alias(alias1).alias(alias2)
//        }
//
//        val actual: Expression<Long> = expression // for type check
//
//        // then
//        val expected = Expressions.alias(
//            Expressions.count(
//                Paths.path(TestTable1::int1),
//                distinct = false,
//            ),
//            alias2,
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    private class TestTable1 {
//        val int1: Int = 1
//    }
//}
