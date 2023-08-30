package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.Selects
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class GreaterThanDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::price)

    private val bigDecimal1 = BigDecimal.valueOf(100)

    private val bigDecimalExpression1 = Expressions.value(bigDecimal1)

    private val subquery1 = Expressions.subquery(
        Selects.select(
            returnType = BigDecimal::class,
            distinct = false,
            select = listOf(Paths.path(Book::price)),
            from = listOf(Entities.entity(Book::class)),
        ),
    )

    @Test
    fun `greaterThan() with a bigDecimal`() {
        // when
        val actual = queryPart {
            expression1.greaterThan(bigDecimal1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThan() with a bigDecimal expression`() {
        // when
        val actual = queryPart {
            expression1.greaterThan(bigDecimalExpression1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThan() with a bigDecimal and the inclusive`() {
        // when
        val actual = queryPart {
            expression1.greaterThan(bigDecimal1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThan() with a bigDecimal expression and the inclusive`() {
        // when
        val actual = queryPart {
            expression1.greaterThan(bigDecimalExpression1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanAll() {
        // when
        val actual = queryPart {
            expression1.greaterThanAll(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThanAll() with the inclusive`() {
        // when
        val actual = queryPart {
            expression1.greaterThanAll(subquery1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualToAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanAny() {
        // when
        val actual = queryPart {
            expression1.greaterThanAny(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThanAny() with the inclusive`() {
        // when
        val actual = queryPart {
            expression1.greaterThanAny(subquery1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualToAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `gt() with a bigDecimal`() {
        // when
        val actual = queryPart {
            expression1.gt(bigDecimal1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `gt() with a bigDecimal expression`() {
        // when
        val actual = queryPart {
            expression1.gt(bigDecimalExpression1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThan(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `gt() with a bigDecimal and the inclusive`() {
        // when
        val actual = queryPart {
            expression1.gt(bigDecimal1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `gt() with a bigDecimal expression and the inclusive`() {
        // when
        val actual = queryPart {
            expression1.gt(bigDecimalExpression1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun gtAll() {
        // when
        val actual = queryPart {
            expression1.gtAll(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `gtAll() with the inclusive`() {
        // when
        val actual = queryPart {
            expression1.gtAll(subquery1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualToAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun gtAny() {
        // when
        val actual = queryPart {
            expression1.gtAny(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `gtAny() with the inclusive`() {
        // when
        val actual = queryPart {
            expression1.gtAny(subquery1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualToAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
