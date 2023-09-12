package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class EqualDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::price)
    private val expression2 = Paths.path(Book::salePrice)

    private val bigDecimal1 = BigDecimal.valueOf(100)

    private val subquery1 = Expressions.subquery(
        SelectQueries.selectQuery(
            returnType = BigDecimal::class,
            distinct = false,
            select = listOf(Paths.path(Book::price)),
            from = listOf(Entities.entity(Book::class)),
        ),
    )

    @Test
    fun `equal() with a bigDecimal`() {
        // when
        val predicate = queryPart {
            expression1.equal(bigDecimal1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.equal(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `equal() with a expression`() {
        // when
        val predicate = queryPart {
            expression1.equal(expression2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.equal(
            expression1,
            expression2,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun equalAll() {
        // when
        val predicate = queryPart {
            expression1.equalAll(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.equalAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun equalAny() {
        // when
        val predicate = queryPart {
            expression1.equalAny(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.equalAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `eq() with a bigDecimal`() {
        // when
        val predicate = queryPart {
            expression1.eq(bigDecimal1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.equal(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `eq() with a expression`() {
        // when
        val predicate = queryPart {
            expression1.eq(expression2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.equal(
            expression1,
            expression2,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun eqAll() {
        // when
        val predicate = queryPart {
            expression1.eqAll(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.equalAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun eqAny() {
        // when
        val predicate = queryPart {
            expression1.eqAny(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.equalAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notEqual() with a bigDecimal`() {
        // when
        val predicate = queryPart {
            expression1.notEqual(bigDecimal1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notEqual(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `notEqual() with a expression`() {
        // when
        val predicate = queryPart {
            expression1.notEqual(expression2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notEqual(
            expression1,
            expression2,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun notEqualAll() {
        // when
        val predicate = queryPart {
            expression1.notEqualAll(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notEqualAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun notEqualAny() {
        // when
        val predicate = queryPart {
            expression1.notEqualAny(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notEqualAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `ne() with a bigDecimal`() {
        // when
        val predicate = queryPart {
            expression1.ne(bigDecimal1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notEqual(
            expression1,
            Expressions.value(bigDecimal1),
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `ne() with a expression`() {
        // when
        val predicate = queryPart {
            expression1.ne(expression2)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notEqual(
            expression1,
            expression2,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun neAll() {
        // when
        val predicate = queryPart {
            expression1.neAll(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notEqualAll(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun neAny() {
        // when
        val predicate = queryPart {
            expression1.neAny(subquery1)
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.notEqualAny(
            expression1,
            subquery1,
        )

        assertThat(actual).isEqualTo(excepted)
    }
}
