package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

class TypeDslTest : AbstractJpqlDslTest() {
    @Test
    fun `type path`() {
        // when
        val expression = testJpql {
            type(path(TestTable1::field1))
        }.toExpression()

        val actual: Expression<KClass<*>> = expression // for type check

        // then
        val expected = Expressions.type(
            Paths.path(TestTable1::field1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val field1: TestField1 = TestSubField1()
    }

    private open class TestField1
    private open class TestSubField1 : TestField1()
}
