package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class LikeDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::title)

    private val string1 = "string1"
    private val char1 = '_'

    private val stringExpression1 = Expressions.value("string1")
    private val charExpression1 = Expressions.value('_')

    @Test
    fun `like() with a string`() {
        // when
        val predicate = queryPart {
            expression1.like(string1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.like(
            value = expression1,
            pattern = Expressions.value(string1),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `like() with a string and a char`() {
        // when
        val predicate = queryPart {
            expression1.like(string1, escape = char1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.like(
            value = expression1,
            pattern = Expressions.value(string1),
            escape = Expressions.value(char1),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `like() with a string expression`() {
        // when
        val predicate = queryPart {
            expression1.like(stringExpression1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.like(
            value = expression1,
            pattern = stringExpression1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `like() with a string expression and a char expression`() {
        // when
        val predicate = queryPart {
            expression1.like(string1, escape = char1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.like(
            value = expression1,
            pattern = stringExpression1,
            escape = charExpression1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notLike() with a string`() {
        // when
        val predicate = queryPart {
            expression1.notLike(string1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notLike(
            value = expression1,
            pattern = Expressions.value(string1),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notLike() with a string and a char`() {
        // when
        val predicate = queryPart {
            expression1.notLike(string1, escape = char1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notLike(
            value = expression1,
            pattern = Expressions.value(string1),
            escape = Expressions.value(char1),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notLike() with a string expression`() {
        // when
        val predicate = queryPart {
            expression1.notLike(stringExpression1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notLike(
            value = expression1,
            pattern = stringExpression1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notLike() with a string expression and a char expression`() {
        // when
        val predicate = queryPart {
            expression1.notLike(string1, escape = char1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notLike(
            value = expression1,
            pattern = stringExpression1,
            escape = charExpression1,
        )

        assertThat(actual).isEqualTo(excepted)
    }
}
