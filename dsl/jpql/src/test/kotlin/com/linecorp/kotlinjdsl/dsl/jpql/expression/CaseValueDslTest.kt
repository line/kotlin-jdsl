package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CaseValueDslTest : WithAssertions {
    private val path1 = Paths.path(Book::price)

    private val bigDecimal1 = BigDecimal.valueOf(100)
    private val bigDecimal2 = BigDecimal.valueOf(200)

    private val bigDecimalExpression1 = Expressions.value(bigDecimal1)
    private val bigDecimalExpression2 = Expressions.value(bigDecimal2)

    private val string1 = "string1"
    private val string2 = "string2"

    private val stringExpression1 = Expressions.value(string1)
    private val stringExpression2 = Expressions.value(string2)

    @Test
    fun `caseValue() with a bigDecimal and a string`() {
        // when
        val expression = queryPart {
            caseValue(path1).`when`(bigDecimal1).then(string1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = path1,
            whens = mapOf(
                Expressions.value(bigDecimal1) to Expressions.value(string1),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseValue() with a bigDecimal expression and a string expression`() {
        // when
        val expression = queryPart {
            caseValue(path1).`when`(bigDecimalExpression1).then(stringExpression1)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = path1,
            whens = mapOf(
                bigDecimalExpression1 to stringExpression1,
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseValue() with bigDecimals and strings`() {
        // when
        val expression = queryPart {
            caseValue(path1)
                .`when`(bigDecimal1).then(string1)
                .`when`(bigDecimal2).then(string2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = path1,
            whens = mapOf(
                Expressions.value(bigDecimal1) to Expressions.value(string1),
                Expressions.value(bigDecimal2) to Expressions.value(string2),
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseValue() with bigDecimal expressions and string expressions`() {
        // when
        val expression = queryPart {
            caseValue(path1)
                .`when`(bigDecimalExpression1).then(stringExpression1)
                .`when`(bigDecimalExpression2).then(stringExpression2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = path1,
            whens = mapOf(
                bigDecimalExpression1 to stringExpression1,
                bigDecimalExpression2 to stringExpression2,
            ),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseValue() with a bigDecimal, a string, and else()`() {
        // when
        val expression = queryPart {
            caseValue(path1)
                .`when`(bigDecimal1).then(string1)
                .`else`(string2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = path1,
            whens = mapOf(
                Expressions.value(bigDecimal1) to Expressions.value(string1),
            ),
            `else` = Expressions.value(string2),
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }

    @Test
    fun `caseValue() with a bigDecimal expression, a string expression, and else()`() {
        // when
        val expression = queryPart {
            caseValue(path1)
                .`when`(bigDecimalExpression1).then(stringExpression1)
                .`else`(stringExpression2)
        }.toExpression()

        val actual: Expression<String> = expression // for type check

        // then
        val expected = Expressions.caseValue(
            value = path1,
            whens = mapOf(
                bigDecimalExpression1 to stringExpression1,
            ),
            `else` = stringExpression2,
        )

        assertThat(actual.toExpression()).isEqualTo(expected)
    }
}
