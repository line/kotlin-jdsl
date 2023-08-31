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

class OrDslTest : WithAssertions {
    private val predicate1 = Predicates.equal(
        Paths.path(Book::price),
        Expressions.value(BigDecimal.valueOf(100)),
    )

    private val predicate2 = Predicates.equal(
        Paths.path(Book::salePrice),
        Expressions.value(BigDecimal.valueOf(200)),
    )

    @Test
    fun `or() with predicates`() {
        // when
        val predicate = queryPart {
            or(
                predicate1,
                null,
                predicate2,
            )
        }.toPredicate()

        val actual: Predicate = predicate // for type check

        // then
        val expected = Predicates.or(
            listOf(
                Predicates.parentheses(predicate1),
                Predicates.parentheses(predicate2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `or() with a predicate and a predicate`() {
        // when
        val predicate = queryPart {
            predicate1.or(predicate2)
        }.toPredicate()

        val actual: Predicate = predicate // for type check

        // then
        val expected = Predicates.or(
            listOf(
                predicate1,
                predicate2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
