package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class FunctionDslTest : WithAssertions {
    private val name1: String = "name1"

    private val stringExpression1 = Expressions.value("string1")
    private val stringExpression2 = Expressions.value("string2")

    private val string1 = "string1"
    private val string2 = "string2"

    @Test
    fun `function() with strings`() {
        // when
        val predicate = queryPart {
            function(Boolean::class, name1, string1, string2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val expected = Predicates.function(
            name1,
            listOf(
                Expressions.value(string1),
                Expressions.value(string2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `function() with string expressions`() {
        // when
        val predicate = queryPart {
            function(Boolean::class, name1, stringExpression1, stringExpression2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val expected = Predicates.function(
            name1,
            listOf(
                stringExpression1,
                stringExpression2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `function() with a string and a string expression`() {
        // when
        val predicate = queryPart {
            function(Boolean::class, name1, string1, stringExpression2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val expected = Predicates.function(
            name1,
            listOf(
                Expressions.value(string1),
                stringExpression2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
