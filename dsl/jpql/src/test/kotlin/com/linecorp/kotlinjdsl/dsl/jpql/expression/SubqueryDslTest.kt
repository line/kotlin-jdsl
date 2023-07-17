package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.Queries
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import org.junit.jupiter.api.Test

class SubqueryDslTest : AbstractJpqlDslTest() {
    @Test
    fun `select asSubquery`() {
        // when
        val select = testJpql {
            select(
                entity(TestTable1::class),
            ).from(
                entity(TestTable1::class),
            )
        }.toQuery()

        val subquery1 = testJpql {
            select(
                entity(TestTable1::class),
            ).from(
                entity(TestTable1::class),
            ).asSubquery()
        }.toExpression()

        val subquery2 = testJpql {
            select.asSubquery()
        }.toExpression()

        val actual1: Expression<TestTable1> = subquery1 // for type check
        val actual2: Expression<TestTable1> = subquery2 // for type check

        // then
        val expected = Expressions.subquery(
            Queries.select<TestTable1>(
                returnType = TestTable1::class,
                select = listOf(Paths.entity(TestTable1::class)),
                distinct = false,
                from = listOf(
                    Paths.entity(TestTable1::class),
                ),
                where = null,
                groupBy = null,
                having = null,
                orderBy = null,
            ),
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    private class TestTable1
}
