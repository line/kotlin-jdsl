package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import java.math.BigDecimal
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class BetweenDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::price)

    private val bigDecimal1 = BigDecimal.valueOf(100)
    private val bigDecimal2 = BigDecimal.valueOf(100)

    private val bigDecimalExpression1 = Expressions.value(BigDecimal.valueOf(100))
    private val bigDecimalExpression2 = Expressions.value(BigDecimal.valueOf(100))

    @Test
    fun `between() with bigDecimals`() {
        // when
        val predicate = queryPart {
            expression1.between(bigDecimal1, bigDecimal2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.between(
            value = expression1,
            min = Expressions.value(bigDecimal1),
            max = Expressions.value(bigDecimal2),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `between() with bigDecimal expressions`() {
        // when
        val predicate = queryPart {
            expression1.between(bigDecimalExpression1, bigDecimalExpression2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.between(
            value = expression1,
            min = bigDecimalExpression1,
            max = bigDecimalExpression2,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notBetween() with bigDecimals`() {
        // when
        val predicate = queryPart {
            expression1.notBetween(bigDecimal1, bigDecimal2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notBetween(
            value = expression1,
            min = Expressions.value(bigDecimal1),
            max = Expressions.value(bigDecimal2),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notBetween() with bigDecimal expressions`() {
        // when
        val predicate = queryPart {
            expression1.notBetween(bigDecimalExpression1, bigDecimalExpression2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notBetween(
            value = expression1,
            min = bigDecimalExpression1,
            max = bigDecimalExpression2,
        )

        assertThat(actual).isEqualTo(excepted)
    }
}
