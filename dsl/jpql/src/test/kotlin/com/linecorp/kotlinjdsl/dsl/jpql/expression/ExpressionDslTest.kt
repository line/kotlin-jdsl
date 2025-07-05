package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class ExpressionDslTest : WithAssertions {
    private val alias1 = "alias1"

    @Test
    fun `expression() with a class and an alias`() {
        // when
        val expression = queryPart {
            expression(String::class, alias1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.expression(
            String::class,
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `expression() with a generic type and an alias`() {
        // when
        val expression = queryPart {
            expression<String>(alias1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.expression(
            String::class,
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `cast() with a expression and a class`() {
        // when
        val expression = queryPart {
            cast(expression(String::class, alias1), Int::class)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.cast(
            Expressions.expression(String::class, alias1),
            Int::class,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `inline cast() with a expression and a class`() {
        // when
        val expression = queryPart {
            cast<Int>(expression(String::class, alias1))
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.cast(
            Expressions.expression(String::class, alias1),
            Int::class,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `left() with two expressions`() {
        // when
        val expression = queryPart {
            left(expression(String::class, alias1), expression(Int::class, "alias2"))
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.left(
            Expressions.expression(String::class, alias1),
            Expressions.expression(Int::class, "alias2"),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `left() literal with two expressions`() {
        // when
        val expression = queryPart {
            left(expression(String::class, alias1), 1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.left(
            Expressions.expression(String::class, alias1),
            Expressions.intLiteral(1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `right() with two expressions`() {
        // when
        val expression = queryPart {
            right(expression(String::class, alias1), expression(Int::class, "alias2"))
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.right(
            Expressions.expression(String::class, alias1),
            Expressions.expression(Int::class, "alias2"),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `right() literal with two expressions`() {
        // when
        val expression = queryPart {
            right(expression(String::class, alias1), 1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.right(
            Expressions.expression(String::class, alias1),
            Expressions.intLiteral(1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `replace() with three expressions`() {
        // when
        val expression = queryPart {
            replace(
                expression(String::class, alias1),
                expression(String::class, "alias2"),
                expression(String::class, "alias3"),
            )
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.replace(
            Expressions.expression(String::class, alias1),
            Expressions.expression(String::class, "alias2"),
            Expressions.expression(String::class, "alias3"),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `replace() literal with three expressions`() {
        // when
        val expression = queryPart {
            replace(
                expression(String::class, alias1),
                "string1",
                "string2",
            )
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.replace(
            Expressions.expression(String::class, alias1),
            Expressions.stringLiteral("string1"),
            Expressions.stringLiteral("string2"),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
