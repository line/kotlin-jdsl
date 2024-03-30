package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class IndexDslTest : WithAssertions {
    private val entity1 = Entities.entity(BookAuthor::class)

    @Test
    fun `index() with a class`() {
        // when
        val expression = queryPart {
            index(BookAuthor::class)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.index(
            entity = Entities.entity(BookAuthor::class),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `index() with a expression`() {
        // when
        val expression = queryPart {
            index(entity1)
        }.toExpression()

        val actual: Expression<Int> = expression // for type check

        // then
        val expected = Expressions.index(
            entity = entity1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
