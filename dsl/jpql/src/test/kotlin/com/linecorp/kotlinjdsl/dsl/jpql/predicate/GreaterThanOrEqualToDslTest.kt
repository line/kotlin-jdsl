package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class GreaterThanOrEqualToDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::price)

    private val bigDecimal1 = BigDecimal.valueOf(100)

    private val bigDecimalExpression1 = Expressions.value(bigDecimal1)

    private val subquery1 = Expressions.subquery(
        SelectQueries.select(
            returnType = BigDecimal::class,
            distinct = false,
            select = listOf(Paths.path(Book::price)),
            from = listOf(Entities.entity(Book::class)),
        ),
    )

    @Test
    fun `greaterThanOrEqualTo() with a bigDecimal`() {
        // when
        val actual = queryPart {
            expression1.greaterThanOrEqualTo(bigDecimal1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `greaterThanOrEqualTo() with a bigDecimal expression`() {
        // when
        val actual = queryPart {
            expression1.greaterThanOrEqualTo(bigDecimalExpression1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanOrEqualToAll() {
        // when
        val actual = queryPart {
            expression1.greaterThanOrEqualToAll(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualToAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun greaterThanOrEqualToAny() {
        // when
        val actual = queryPart {
            expression1.greaterThanOrEqualToAny(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualToAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `ge() with a bigDecimal`() {
        // when
        val actual = queryPart {
            expression1.ge(bigDecimal1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `ge() with a bigDecimal expression`() {
        // when
        val actual = queryPart {
            expression1.ge(bigDecimalExpression1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualTo(
            expression1,
            bigDecimalExpression1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun geAll() {
        // when
        val actual = queryPart {
            expression1.geAll(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualToAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun geAny() {
        // when
        val actual = queryPart {
            expression1.geAny(subquery1)
        }.toPredicate()

        // then
        val expected = Predicates.greaterThanOrEqualToAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
