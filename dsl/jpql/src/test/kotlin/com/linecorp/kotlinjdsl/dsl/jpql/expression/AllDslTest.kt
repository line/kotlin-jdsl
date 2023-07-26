//package com.linecorp.kotlinjdsl.dsl.jpql.expression
//
//import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
//import com.linecorp.kotlinjdsl.querymodel.jpql.delete.Deletes
//import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
//import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
//import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
//import org.junit.jupiter.api.Test
//
//class AllDslTest : AbstractJpqlDslTest() {
//    @Test
//    fun `select asSubquery`() {
//        // when
//        val expression = testJpql {
//            val subquery = select(
//                entity(TestTable1::class),
//            ).from(
//                entity(TestTable1::class),
//            ).asSubquery()
//
//            all(subquery)
//        }.toExpression()
//
//        val actual: Expression<TestTable1> = expression // for type check
//
//        // then
//        val expected = Expressions.all(
//            Expressions.subquery(
//                Deletes.select<TestTable1>(
//                    returnType = TestTable1::class,
//                    select = listOf(Entities.entity(TestTable1::class)),
//                    distinct = false,
//                    from = listOf(
//                        Entities.entity(TestTable1::class),
//                    ),
//                    where = null,
//                    groupBy = null,
//                    having = null,
//                    orderBy = null,
//                ),
//            )
//        )
//
//        assertThat(actual).isEqualTo(expected)
//    }
//
//    private class TestTable1
//}
