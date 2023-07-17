package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

class TypeDslTest : AbstractJpqlDslTest() {
    @Test
    fun `type path`() {
        // when
        val expression = testJpql {
            type(path(TestTable::table1))
        }.toExpression()

        val actual: Expression<KClass<SuperTable>> = expression // for type check

        // then
        val expected = Expressions.type(
            Paths.path(TestTable::table1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `type nullable path`() {
        // when
        val expression = testJpql {
            type(path(TestTable::nullableTable1))
        }.toExpression()

        val actual: Expression<KClass<SuperTable>?> = expression // for type check

        // then
        val expected = Expressions.type(
            Paths.path(TestTable::nullableTable1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable {
        val table1: SuperTable = SubTable()
        val nullableTable1: SuperTable? = null
    }

    private open class SuperTable
    private open class SubTable : SuperTable()
}
