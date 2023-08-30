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

class LessThanDslTest : WithAssertions {
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
    fun `lessThan() with a bigDecimal`() {
        // when
        val actual = queryPart {
            expression1.lessThan(bigDecimal1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `lessThan() with a bigDecimal expression`() {
        // when
        val actual = queryPart {
            expression1.lessThan(bigDecimalExpression1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `lessThan() with a bigDecimal and the inclusive`() {
        // when
        val actual = queryPart {
            expression1.lessThan(bigDecimal1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `lessThan() with a bigDecimal expression and the inclusive`() {
        // when
        val actual = queryPart {
            expression1.lessThan(bigDecimalExpression1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanAll() {
        // when
        val actual = queryPart {
            expression1.lessThanAll(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `lessThanAll() with the inclusive`() {
        // when
        val actual = queryPart {
            expression1.lessThanAll(subquery1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualToAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun lessThanAny() {
        // when
        val actual = queryPart {
            expression1.lessThanAny(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `lessThanAny() with the inclusive`() {
        // when
        val actual = queryPart {
            expression1.lessThanAny(subquery1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualToAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `lt() with a bigDecimal`() {
        // when
        val actual = queryPart {
            expression1.lt(bigDecimal1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `lt() with a bigDecimal expression`() {
        // when
        val actual = queryPart {
            expression1.lt(bigDecimalExpression1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThan(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `lt() with a bigDecimal and the inclusive`() {
        // when
        val actual = queryPart {
            expression1.lt(bigDecimal1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `lt() with a bigDecimal expression and the inclusive`() {
        // when
        val actual = queryPart {
            expression1.lt(bigDecimalExpression1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualTo(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun ltAll() {
        // when
        val actual = queryPart {
            expression1.ltAll(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `ltAll() with the inclusive`() {
        // when
        val actual = queryPart {
            expression1.ltAll(subquery1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualToAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun ltAny() {
        // when
        val actual = queryPart {
            expression1.ltAny(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `ltAny() with the inclusive`() {
        // when
        val actual = queryPart {
            expression1.ltAny(subquery1, inclusive = true)
        }.toPredicate()

        // then
        val expected = Predicates.lessThanOrEqualToAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
