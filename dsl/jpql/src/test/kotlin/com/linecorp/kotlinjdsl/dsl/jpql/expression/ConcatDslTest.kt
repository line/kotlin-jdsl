package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.Experimental
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

@Experimental
class ConcatDslTest : WithAssertions {
    private val string1 = "string1"
    private val string2 = "string2"
    private val string3 = "string3"
    private val null1: String? = null
    private val null2: String? = null
    private val null3: String? = null

    private val stringExpression1 = Expressions.value("string1")
    private val stringExpression2 = Expressions.value("string2")
    private val stringExpression3 = Expressions.value("string3")

    @Test
    fun `concat() with strings`() {
        // when
        val expression = queryPart {
            concat(string1, string2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.concat(
            value1 = Expressions.value(string1),
            value2 = Expressions.value(string2),
            others = emptyList(),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `concat() with strings and others`() {
        // when
        val expression = queryPart {
            concat(string1, string2, string3)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.concat(
            value1 = Expressions.value(string1),
            value2 = Expressions.value(string2),
            others = listOf(Expressions.value(string3)),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `concat() with a string and a null`() {
        // when
        val expression = queryPart {
            concat(string1, null1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.concat(
            value1 = Expressions.value(string1),
            value2 = Expressions.value(null1),
            others = emptyList(),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `concat() with nulls`() {
        // when
        val expression = queryPart {
            concat(null1, null2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.concat(
            value1 = Expressions.value(null1),
            value2 = Expressions.value(null2),
            others = emptyList(),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `concat() with nulls and others`() {
        // when
        val expression = queryPart {
            concat(null1, null2, null3)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.concat(
            value1 = Expressions.value(null1),
            value2 = Expressions.value(null2),
            others = listOf(Expressions.value(null3)),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `concat() with string expressions`() {
        // when
        val expression = queryPart {
            concat(stringExpression1, stringExpression2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.concat(
            value1 = stringExpression1,
            value2 = stringExpression2,
            others = emptyList(),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `concat() with string expressions and others`() {
        // when
        val expression = queryPart {
            concat(stringExpression1, stringExpression2, stringExpression3)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.concat(
            value1 = stringExpression1,
            value2 = stringExpression2,
            others = listOf(stringExpression3),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
