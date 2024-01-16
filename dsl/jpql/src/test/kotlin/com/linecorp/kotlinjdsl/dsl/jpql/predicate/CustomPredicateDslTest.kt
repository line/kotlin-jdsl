package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class CustomPredicateDslTest : WithAssertions {
    private val template1: String = "template1"

    private val stringExpression1 = Expressions.value("string1")
    private val stringExpression2 = Expressions.value("string2")

    private val string1 = "string1"
    private val string2 = "string2"

    @Test
    fun `customPredicate() with strings`() {
        // when
        val predicate = queryPart {
            customPredicate(template1, string1, string2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val expected = Predicates.customPredicate(
            template1,
            listOf(
                Expressions.value(string1),
                Expressions.value(string2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `customPredicate() with string expressions`() {
        // when
        val predicate = queryPart {
            customPredicate(template1, stringExpression1, stringExpression2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val expected = Predicates.customPredicate(
            template1,
            listOf(
                stringExpression1,
                stringExpression2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
